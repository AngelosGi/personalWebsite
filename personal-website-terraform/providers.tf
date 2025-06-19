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
