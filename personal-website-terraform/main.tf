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

  # User data for cloud-init configuration
  user_data = file(var.user_data_file) # Reads the cloud-init.yaml file

  # Ensure the SSH key is created before the server
  depends_on = [
    hcloud_ssh_key.default
  ]

  lifecycle {
    ignore_changes = [user_data]
  }
}
