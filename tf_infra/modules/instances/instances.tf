data "aws_ami" "ubuntu" {
  most_recent = true
  owners      = ["099720109477"] # Canonical

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"]
    #ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }

  filter {
    name   = "architecture"
    values = ["x86_64"]
  }
}

data "template_file" "user_data" {
  template = file("${path.module}/user_data.sh")
}


resource "aws_instance" "public_instance" {
  count                       = length(var.public_instance_count)
  ami                         = data.aws_ami.ubuntu.id
  instance_type               = var.instance_type
  key_name                    = var.key_name
  vpc_security_group_ids      = [var.security_group_id]
  subnet_id                   = var.public_subnet_ids[count.index]
  user_data                   = data.template_file.user_data.rendered
  tags = merge(var.tags,{Name = format("%s-%s-%s-server-${count.index + 1}","public",var.appname,var.env)})
}