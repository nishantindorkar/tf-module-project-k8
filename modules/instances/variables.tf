variable "instance_type" {
  type = string
}

variable "public_subnet_ids" {
  type = list(string)
}

variable "security_group_id" {
  type = string
}

variable "env" {
  type = string
}

variable "appname" {
  type = string
}

variable "key_name" {
  type = string
}
variable "tags" {
  type = map(string)
  default = {
    Name = "other"
  }
}

variable "public_instance_count" {
  type = list(string)
}