variable "appname" {
  type    = string
  default = "web"
}
variable "env" {
  type    = string
  default = "development"
}
variable "instance_type" {
  type    = string
  default = "t2.micro"
}
variable "key_name" {
  type    = string
  default = "first-virginia-key"
}
variable "internal" {
  type    = bool
  default = false
}
variable "type" {
  type    = string
  default = "application"
}