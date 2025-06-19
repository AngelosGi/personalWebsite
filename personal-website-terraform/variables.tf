variable "hcloud_token" {
  description = "Hetzner Cloud API token."
  type        = string
  sensitive   = true 
}

variable "server_name" {
  description = "Name for the Hetzner Cloud server."
  type        = string
  default     = "personal-website-vps-tf"
}

variable "server_type" {
  description = "Hetzner Cloud server type (e.g., cpx11, cx21)."
  type        = string
  default     = "cpx11" 
}

variable "server_image" {
  description = "Hetzner Cloud server image."
  type        = string
  default     = "ubuntu-22.04"
}

variable "server_location" {
  description = "Hetzner Cloud server location."
  type        = string
  default     = "nbg1" 
}

variable "ssh_key_name" {
  description = "Name for the SSH key resource in Hetzner Cloud."
  type        = string
  default     = "tf-hetzner-ansible-key"
}

variable "ssh_public_key_path" {
  description = "Path to the public SSH key file to be uploaded to Hetzner for root access."
  type        = string
  # Example: default = "~/.ssh/your_key.pub"
  # runtime or .tfvars or environment variable
}

variable "user_data_file" {
  description = "Path to the cloud-init user data file."
  type        = string
  default     = "./cloud-init.yaml"
}
