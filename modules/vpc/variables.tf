variable "cidr_blocks" {
  type = string
}

variable "cidr_blocks_defualt" {
  type = string
}

variable "public_cidr_blocks" {
  type = list(string)
}

variable "availability_zones" {
  type = list(string)
}

variable "tags" {
  type = map(string)
  default = {
    Name = "other"
  }
}

variable "appname" {
  type = string
}

variable "env" {
  type = string
}

variable "map_public_ip_on_launch" {
  type = bool
}
