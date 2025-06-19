variable "hcloud_token" {
  description = "Hetzner Cloud API token."
  type        = string
  sensitive   = true # So Terraform doesn't output it in logs
}

variable "server_name" {
  description = "Name for the Hetzner Cloud server."
  type        = string
  default     = "personal-website-vps-tf"
}

variable "server_type" {
  description = "Hetzner Cloud server type (e.g., cpx11, cx21)."
  type        = string
  default     = "cpx11" # A good starting point
}

variable "server_image" {
  description = "Hetzner Cloud server image (e.g., ubuntu-22.04)."
  type        = string
  default     = "ubuntu-22.04"
}

variable "server_location" {
  description = "Hetzner Cloud server location (e.g., nbg1, fsn1, hel1)."
  type        = string
  default     = "nbg1" # Nuremberg
}

variable "ssh_key_name" {
  description = "Name for the SSH key resource in Hetzner Cloud."
  type        = string
  default     = "tf-hetzner-ansible-key"
}

variable "ssh_public_key_path" {
  description = "Path to the public SSH key file to be uploaded to Hetzner for root access."
  type        = string
  # Example: default = "~/.ssh/hetzner_rsa.pub"
  # It's better to provide this at runtime or via a .tfvars file
}

variable "user_data_file" {
  description = "Path to the cloud-init user data file."
  type        = string
  default     = "./cloud-init.yaml"
}
