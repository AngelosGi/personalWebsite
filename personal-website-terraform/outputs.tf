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
