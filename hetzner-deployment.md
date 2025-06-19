

## Step-by-Step Deployment Guide

This guide provides a tutorial-style walkthrough for deploying the application from scratch, assuming you have just cloned the repository containing the `personal-website` application code, the `personal-website-terraform` directory, and the `personal-website-ansible` directory.

### 1. Initial Setup & Prerequisites

Before starting, ensure you have:

*   A **Hetzner Cloud account** and an **API token**.
*   A registered **domain name** (e.g., `anggi.io`).
*   Your application's Docker image pushed to **Docker Hub** (e.g., `anggian/personal-website:latest`).
*   **Terraform CLI** installed locally.
*   **Ansible CLI** installed locally.
*   An **SSH key pair** (e.g., `~/.ssh/hetzner_rsa` for the private key and `~/.ssh/hetzner_rsa.pub` for the public key).
*   **Git** installed locally.

### 2. Phase 1: Provision Infrastructure with Terraform

This phase creates the server on Hetzner Cloud.

**a. Navigate to the Terraform Directory**

Open your terminal and change to the Terraform project directory:
```bash
cd /path/to/your/projects/personal-website-terraform
```

**b. Configure Secrets as Environment Variables**

Set your Hetzner API token and the path to your public SSH key. These are used by Terraform to authenticate and set up the server.
```bash
export TF_VAR_hcloud_token="YOUR_HETZNER_API_TOKEN"
export TF_VAR_ssh_public_key_path="~/.ssh/hetzner_rsa.pub"
```
*   Replace `YOUR_HETZNER_API_TOKEN` with your actual token.
*   Replace `~/.ssh/hetzner_rsa.pub` with the correct path to your public SSH key.

**c. Update `cloud-init.yaml` with Your Public SSH Key**

Open the `personal-website-terraform/cloud-init.yaml` file. Find the `ssh_authorized_keys` section and replace the placeholder with the *entire content* of your public SSH key file (`~/.ssh/hetzner_rsa.pub`).
```yaml
    users:
      - name: anggi
        # ... other user settings ...
        ssh_authorized_keys:
          - ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQC... your_email@example.com # Replace this line
```

**d. Initialize Terraform**

This command downloads the necessary provider plugins. Run it once per project.
```bash
terraform init
```

**e. Plan the Infrastructure**

This command shows you what Terraform will create or change. Review the output carefully.
```bash
terraform plan
```
You should see a plan to add 2 resources (an SSH key and a server).

**f. Apply the Infrastructure Plan**

This command creates the actual resources in your Hetzner Cloud account.
```bash
terraform apply
```
Type `yes` when prompted to confirm. This may take a few minutes.
Upon completion, Terraform will output the server's name, IPv4 address, and IPv6 address. **Copy the IPv4 address** – you'll need it for Ansible.

### 3. Phase 2: Configure Server with Ansible

This phase configures the newly created server and deploys your application.

**a. Navigate to the Ansible Directory**

```bash
cd /path/to/your/projects/personal-website-ansible
```

**b. Update the Inventory File**

Open `personal-website-ansible/inventory`. Replace `YOUR_SERVER_IP_FROM_TERRAFORM` with the actual IPv4 address of your server (obtained from the `terraform apply` output). Also, ensure `ansible_user` matches the user created by `cloud-init` (default is `anggi`).
```ini
[webservers]
91.99.197.234 ansible_user=anggi ansible_python_interpreter=/usr/bin/python3
```

**c. Configure Ansible (`ansible.cfg`)**

Open `personal-website-ansible/ansible.cfg`. Ensure the `private_key_file` path points to your local **private** SSH key (e.g., `~/.ssh/hetzner_rsa`).
```ini
private_key_file  = ~/.ssh/hetzner_rsa
```

**d. Configure Variables (`group_vars/all/vars.yml`)**

Open `personal-website-ansible/group_vars/all/vars.yml`. Review all variables and update them if necessary, especially:
*   `app_docker_image`: Ensure this matches your image on Docker Hub.
*   `app_domain` and `app_www_domain`: Set to your domain.
*   `certbot_email`: Your email for Let's Encrypt SSL notifications.
*   `app_initial_user`: The username for the initial application user (if your app creates one).

**e. Configure Secrets (`group_vars/all/vault.yml`)**

This file stores sensitive data like database passwords.
*   If it's your first time, create it: `ansible-vault create group_vars/all/vault.yml`
*   To edit it: `ansible-vault edit group_vars/all/vault.yml`
Set a strong vault password when prompted. Inside the file, define your secrets:
```yaml
postgres_password: "YOUR_STRONG_DB_PASSWORD"
app_initial_password: "YOUR_STRONG_APP_ADMIN_PASSWORD" # Password for app_initial_user
```

**f. Test Ansible Connection (Optional but Recommended)**

Verify Ansible can connect to your new server:
```bash
ansible webservers -m ping
```
You should see a green "pong" success message. If not, troubleshoot SSH key paths, permissions (`chmod 600 ~/.ssh/hetzner_rsa`), or the IP address in the inventory.

**g. Run the Ansible Playbook (Initial Deployment - HTTP Only)**

For the first run, it's wise to deploy without SSL to test basic functionality.
Open `personal-website-ansible/playbook.yml` and ensure the `certbot_setup` role is commented out:
```yaml
      roles:
        # ... other roles ...
        # - role: certbot_setup
```
Then, run the playbook:
```bash
ansible-playbook playbook.yml --ask-vault-pass
```
Enter your vault password when prompted. This will configure the server and deploy your application.

**h. Verify HTTP Deployment**

Open a web browser and navigate to `http://YOUR_SERVER_IP` (e.g., `http://91.99.197.234`). You should see your application running. If you see the "Welcome to Nginx" page, ensure the `Disable the default Nginx site` task in `roles/nginx_setup/tasks/main.yml` ran correctly.

### 4. Go Live: Point DNS and Enable HTTPS

**a. Update DNS Records**

Go to your domain registrar's DNS management panel. Create or update the "A" records for your domain (`anggi.io`) and subdomain (`www.anggi.io`) to point to your new server's IP address.

**b. Wait for DNS Propagation**

DNS changes can take time to propagate (minutes to hours). You can use online tools like [DNS Checker](https://dnschecker.org/) to monitor this.

**c. Enable HTTPS with Certbot**

Once DNS has propagated and your domain points to the new server:
1.  Open `personal-website-ansible/playbook.yml`.
2.  **Uncomment** the `certbot_setup` role:
    ```yaml
          # ... other roles ...
          - role: certbot_setup
    ```
3.  Save the file.
4.  Run the playbook again:
    ```bash
    ansible-playbook playbook.yml --ask-vault-pass
    ```
This will run the Certbot role, obtain SSL certificates, and configure Nginx for HTTPS.

**d. Verify HTTPS Deployment**

Open your web browser and navigate to `https://your_domain_name` (e.g., `https://anggi.io`). You should see your site served over HTTPS with a valid SSL certificate.




# Deploying the Personal Website to Hetzner Cloud

This document outlines the two-phase process for deploying the Personal Website application to a Hetzner Cloud VPS using Terraform for infrastructure provisioning and Ansible for server configuration and application deployment.

## Overview

The deployment is divided into two main phases:

1.  **Phase 1: Infrastructure Provisioning (Terraform)**: Creates the virtual server on Hetzner Cloud and prepares it with a basic configuration (user, SSH access, Python).
2.  **Phase 2: Server Configuration & Application Deployment (Ansible)**: Configures the provisioned server by installing necessary software (Docker, Nginx), deploying the application containers, setting up a reverse proxy, and securing the site with SSL.

## Prerequisites

Before you begin, ensure you have the following:

*   **Hetzner Cloud Account**: With an API token generated.
*   **Domain Name**: Registered and accessible for DNS configuration.
*   **Docker Hub Account**: Your application's Docker image should be pushed to Docker Hub (e.g., `anggian/personal-website:latest`).
*   **Local Tools**:
    *   Terraform CLI
    *   Ansible CLI
    *   An SSH key pair (e.g., `~/.ssh/hetzner_rsa` and `~/.ssh/hetzner_rsa.pub`)
*   **Git**: For version control of these configuration files.

---

## Phase 1: Infrastructure Provisioning with Terraform

This phase uses Terraform to automatically create the cloud server on Hetzner Cloud.

### Directory Structure

All Terraform files are located in the `personal-website-terraform/` directory:

```
personal-website-terraform/
├── providers.tf
├── variables.tf
├── main.tf
├── outputs.tf
└── cloud-init.yaml
```

### File-by-File Explanation

**1. `providers.tf`**

*   **Purpose**: Specifies the cloud provider (Hetzner Cloud) and configures its authentication.
*   **Content**:
    ```terraform
    terraform {
      required_providers {
        hcloud = {
          source  = "hetznercloud/hcloud"
          version = "~> 1.46" # Use a recent version, check latest on Terraform Registry
        }
      }
    }

    provider "hcloud" {
      token = var.hcloud_token
    }
    ```
*   **Details**: Declares the `hcloud` provider and links it to the `hcloud_token` variable for API access.

**2. `variables.tf`**

*   **Purpose**: Defines input variables for the Terraform project, allowing customization.
*   **Key Variables**:
    *   `hcloud_token` (string, sensitive): Your Hetzner Cloud API token.
    *   `server_name` (string, default: "personal-website-vps-tf"): Name for the server.
    *   `server_type` (string, default: "cpx11"): Hetzner server type.
    *   `server_image` (string, default: "ubuntu-22.04"): Server operating system image.
    *   `server_location` (string, default: "nbg1"): Server location.
    *   `ssh_key_name` (string, default: "tf-hetzner-ansible-key"): Name for the SSH key resource in Hetzner.
    *   `ssh_public_key_path` (string): Path to your local public SSH key (e.g., `~/.ssh/hetzner_rsa.pub`) to be uploaded for root access.
    *   `user_data_file` (string, default: "./cloud-init.yaml"): Path to the cloud-init script.

**3. `cloud-init.yaml`**

*   **Purpose**: A bootstrap script executed on the server's first boot to prepare it for Ansible.
*   **Key Actions**:
    *   Updates package lists and upgrades installed packages.
    *   Installs `python3`, `python3-pip`, `ufw` (firewall), and `git`.
    *   Creates a user (default: `anggi`) with passwordless `sudo` privileges.
    *   Adds your specified public SSH key to the `anggi` user's `authorized_keys` for Ansible access.
    *   Configures basic UFW rules: deny incoming, allow outgoing, allow SSH.
    *   Ensures the SSH service is enabled and running.

*   **Content**:
    ```yaml
    #cloud-config
    # This script is executed on the first boot of the server

    # 1. Update package lists and upgrade installed packages
    package_update: true
    package_upgrade: true

    # 2. Install necessary packages for Ansible
    packages:
      - python3
      - python3-pip
      - ufw
      - git

    # 3. Create the 'anggi' user for Ansible to connect with
    users:
      - name: anggi # Or your desired non-root username
        sudo: ALL=(ALL) NOPASSWD:ALL
        groups: users, sudo
        shell: /bin/bash
        ssh_authorized_keys:
          - YOUR_PUBLIC_SSH_KEY_CONTENT_HERE # e.g., ssh-rsa AAAAB3N... your_email@example.com

    # 4. Run final setup commands
    runcmd:
      # Enable UFW firewall and set basic rules
      - ufw default deny incoming
      - ufw default allow outgoing
      - ufw allow ssh
      - ufw --force enable

      # Ensure ssh service is running
      - systemctl enable ssh
      - systemctl start ssh
    ```
    **Note**: Replace `YOUR_PUBLIC_SSH_KEY_CONTENT_HERE` with the actual content of your public SSH key.

**4. `main.tf`**

*   **Purpose**: Defines the actual cloud resources to be created.
*   **Key Resources**:
    *   `hcloud_ssh_key "default"`: Uploads the public SSH key (from `var.ssh_public_key_path`) to Hetzner.
    *   `hcloud_server "web_server"`: Creates the server using defined variables. Assigns the uploaded SSH key for root access and passes the `cloud-init.yaml` content as `user_data`. Includes `depends_on` for proper resource creation order and `lifecycle { ignore_changes = [user_data] }`.
*   **Content**:
    ```terraform
    # 1. Upload your SSH public key to Hetzner Cloud
    resource "hcloud_ssh_key" "default" {
      name       = var.ssh_key_name
      public_key = file(pathexpand(var.ssh_public_key_path)) # Reads public key from file
    }

    # 2. Define the Hetzner Cloud server
    resource "hcloud_server" "web_server" {
      name        = var.server_name
      server_type = var.server_type
      image       = var.server_image
      location    = var.server_location
      ssh_keys    = [hcloud_ssh_key.default.name] # Assign the uploaded SSH key for root access

      user_data = file(var.user_data_file) # Reads the cloud-init.yaml file

      depends_on = [
        hcloud_ssh_key.default
      ]

      lifecycle {
        ignore_changes = [user_data]
      }
    }
    ```

**5. `outputs.tf`**

*   **Purpose**: Declares information to be displayed after Terraform successfully applies the configuration.
*   **Key Outputs**:
    *   `server_public_ip_v4`: The server's IPv4 address.
    *   `server_public_ip_v6`: The server's IPv6 address.
    *   `server_name`: The name of the created server.
*   **Content**:
    ```terraform
    output "server_public_ip_v4" {
      description = "Public IPv4 address of the server."
      value       = hcloud_server.web_server.ipv4_address
    }

    output "server_public_ip_v6" {
      description = "Public IPv6 address of the server."
      value       = hcloud_server.web_server.ipv6_address
    }

    output "server_name" {
      description = "Name of the server."
      value       = hcloud_server.web_server.name
    }
    ```

### How to Use Terraform

1.  **Navigate**: `cd personal-website-terraform`
2.  **Set Secrets**: Provide your Hetzner API token and SSH key path as environment variables:
    ```bash
    export TF_VAR_hcloud_token="YOUR_HETZNER_API_TOKEN"
    export TF_VAR_ssh_public_key_path="~/.ssh/hetzner_rsa.pub" # Or your actual key path
    ```
3.  **Initialize**: `terraform init` (Run once per project or after provider changes)
4.  **Plan**: `terraform plan` (Review the proposed changes)
5.  **Apply**: `terraform apply` (Type `yes` to confirm and create resources)

After applying, Terraform will output the server's IP address.

---

## Phase 2: Server Configuration & Application Deployment with Ansible

This phase uses Ansible to configure the provisioned server and deploy the application.

### Directory Structure

All Ansible files are located in the `personal-website-ansible/` directory:

```
personal-website-ansible/
├── inventory
├── ansible.cfg
├── playbook.yml
├── group_vars/
│   └── all/
│       ├── vars.yml
│       └── vault.yml
└── roles/
    ├── common_base/
    │   ├── tasks/main.yml
    │   └── handlers/main.yml
    ├── docker_setup/
    │   └── tasks/main.yml
    ├── app_deployment/
    │   ├── tasks/main.yml
    │   └── templates/
    │       ├── docker-compose.yml.j2
    │       └── .env.j2
    ├── nginx_setup/
    │   ├── tasks/main.yml
    │   ├── handlers/main.yml
    │   └── templates/anggi.io.conf.j2
    └── certbot_setup/
        └── tasks/main.yml
```

### File-by-File Explanation

**1. `inventory`**

*   **Purpose**: Lists the servers (hosts) Ansible will manage.
*   **Content**:
    ```ini
    # inventory
    [webservers]
    YOUR_SERVER_IP_FROM_TERRAFORM ansible_user=anggi ansible_python_interpreter=/usr/bin/python3
    ```
    Replace `YOUR_SERVER_IP_FROM_TERRAFORM` with the actual IP and `anggi` with the user created by cloud-init if different.

**2. `ansible.cfg`**

*   **Purpose**: Project-specific Ansible configuration.
*   **Content**:
    ```ini
    # ansible.cfg
    [defaults]
    inventory         = ./inventory
    remote_user       = anggi
    private_key_file  = ~/.ssh/hetzner_rsa # Path to your PRIVATE SSH key
    host_key_checking = False
    deprecation_warnings = False

    [privilege_escalation]
    become              = True
    become_method       = sudo
    become_user         = root
    become_ask_pass     = False
    ```
    Ensure `private_key_file` points to your local private SSH key.

**3. `group_vars/all/vars.yml`**

*   **Purpose**: Defines non-sensitive variables applicable to all hosts.
*   **Content**:
    ```yaml
    # group_vars/all/vars.yml
    ---
    system_user: anggi
    ssh_port: 22
    app_name: personalWebsite
    app_docker_image: "anggian/personal-website:latest"
    app_path: "/home/{{ system_user }}/{{ app_name }}"
    app_domain: "anggi.io"
    app_www_domain: "www.anggi.io"
    app_initial_user: "anggi"
    db_service_name: "db"
    db_port: 5432
    postgres_db_name: "postgres"
    postgres_user: "Anggi"
    certbot_email: "aggelos.gian.sn@gmail.com" # Your email for Let's Encrypt
    db_container_name: "personal-website-db"
    app_container_name: "personal-website-app"
    ```
    Update `certbot_email` and other values as needed.

**4. `group_vars/all/vault.yml`**

*   **Purpose**: Stores sensitive data, encrypted with `ansible-vault`.
*   **Content (before encryption)**:
    ```yaml
    # This file is encrypted with ansible-vault.
    postgres_password: "YOUR_STRONG_DB_PASSWORD"
    app_initial_password: "YOUR_STRONG_APP_ADMIN_PASSWORD"
    ```
*   **Creation**: Use `ansible-vault create group_vars/all/vault.yml`.
*   **Editing**: Use `ansible-vault edit group_vars/all/vault.yml`.

**5. `playbook.yml`**

*   **Purpose**: The main playbook defining the sequence of roles to apply.
*   **Content**:
    ```yaml
    # playbook.yml
    ---
    - hosts: webservers
      become: yes
      vars_files:
        - group_vars/all/vars.yml
        - group_vars/all/vault.yml

      roles:
        - role: common_base
        - role: docker_setup
        - role: app_deployment
        - role: nginx_setup
        # - role: certbot_setup # Uncomment when DNS is ready
    ```

### Roles Explained

*   **`common_base`**:
    *   **Tasks (`roles/common_base/tasks/main.yml`)**:
        *   Waits for `apt-lock`.
        *   Installs `ufw` and `fail2ban`.
        *   Configures UFW: default deny, allow SSH, HTTP, HTTPS.
        *   Ensures `fail2ban` service is active.
        *   Hardens SSH: disables root login and password authentication.
    *   **Handlers (`roles/common_base/handlers/main.yml`)**:
        *   Restarts `sshd` if its configuration changes.

*   **`docker_setup`**:
    *   **Tasks (`roles/docker_setup/tasks/main.yml`)**:
        *   Installs Docker prerequisites.
        *   Adds Docker's GPG key and repository.
        *   Installs `docker-ce`, `docker-ce-cli`, `containerd.io`, `docker-buildx-plugin`, `docker-compose-plugin`.
        *   Ensures Docker service is active.
        *   Adds `system_user` to the `docker` group.

*   **`app_deployment`**:
    *   **Tasks (`roles/app_deployment/tasks/main.yml`)**:
        *   Creates the application directory (`{{ app_path }}`).
        *   Templates `docker-compose.yml` and `.env` files to the server.
        *   Pulls the latest Docker images specified in `docker-compose.yml`.
        *   Starts application services using Docker Compose.
    *   **Templates**:
        *   `docker-compose.yml.j2`: Defines `db` and `app` services, using variables for image names, container names, ports, and environment variables (including database credentials and initial app user credentials from the vault).
        *   `.env.j2`: Provides environment variables for Docker Compose, primarily database credentials.

*   **`nginx_setup`**:
    *   **Tasks (`roles/nginx_setup/tasks/main.yml`)**:
        *   Installs Nginx.
        *   Templates an Nginx site configuration for `{{ app_domain }}`.
        *   Enables the new site by creating a symlink.
        *   Disables the default Nginx site to prevent conflicts.
        *   Ensures Nginx service is active.
    *   **Handlers (`roles/nginx_setup/handlers/main.yml`)**:
        *   Reloads Nginx if its configuration changes.
    *   **Templates (`roles/nginx_setup/templates/anggi.io.conf.j2`)**:
        *   Configures Nginx to listen on port 80 and proxy requests for `{{ app_domain }}` and `{{ app_www_domain }}` to `http://localhost:8080` (where the app container runs).

*   **`certbot_setup`**:
    *   **Tasks (`roles/certbot_setup/tasks/main.yml`)**:
        *   Installs Certbot and its Nginx plugin.
        *   Runs Certbot to obtain an SSL certificate for `{{ app_domain }}` and `{{ app_www_domain }}`, automatically configuring Nginx for HTTPS and HTTP-to-HTTPS redirection. Uses `creates` argument for idempotency.
        *   Ensures Certbot's auto-renewal timer is active.

### How to Use Ansible

1.  **Navigate**: `cd personal-website-ansible`
2.  **Update Inventory**: Ensure `inventory` has the correct server IP.
3.  **Update Variables**: Check `group_vars/all/vars.yml` and update `certbot_email` and any other necessary values.
4.  **Update Vault**: Ensure `group_vars/all/vault.yml` contains the correct sensitive passwords using `ansible-vault edit group_vars/all/vault.yml`.
5.  **DNS Prerequisite for Certbot**: Before running the `certbot_setup` role, ensure your domain's A records for `anggi.io` and `www.anggi.io` point to the new server's IP address.
6.  **Run Playbook**:
    ```bash
    ansible-playbook playbook.yml --ask-vault-pass
    ```
    Enter your vault password when prompted.

---

## Deployment Workflow Summary

1.  **Prepare**:
    *   Ensure all prerequisites are met.
    *   Push your application's Docker image to Docker Hub.
2.  **Provision Infrastructure (Terraform)**:
    *   Set `TF_VAR_hcloud_token` and `TF_VAR_ssh_public_key_path` environment variables.
    *   In `personal-website-terraform/`, run `terraform init`, then `terraform plan`, then `terraform apply`.
    *   Note the outputted server IP address.
3.  **Configure Server (Ansible)**:
    *   In `personal-website-ansible/`, update `inventory` with the new IP.
    *   Verify variables in `group_vars/all/vars.yml` and `group_vars/all/vault.yml`.
    *   Run `ansible-playbook playbook.yml --ask-vault-pass`.
        *   Initially, you might run with the `certbot_setup` role commented out in `playbook.yml` to test HTTP access via IP.
4.  **Go Live & Enable HTTPS**:
    *   Once the application is confirmed working via IP address (`http://YOUR_SERVER_IP`), update your DNS A records for `anggi.io` and `www.anggi.io` to point to the new server IP.
    *   Wait for DNS propagation.
    *   Uncomment the `certbot_setup` role in `playbook.yml`.
    *   Run `ansible-playbook playbook.yml --ask-vault-pass` again to obtain and install the SSL certificate.
5.  **Verify**: Access your site via `https://anggi.io`.

---
