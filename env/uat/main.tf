provider "aws" {
  profile = var.profile
  region  = var.region
}

module "vpc" {
  source                  = "../../modules/vpc"
  cidr_blocks             = var.cidr_blocks
  cidr_blocks_defualt     = var.cidr_blocks_defualt
  public_cidr_blocks      = var.public_cidr_blocks
  availability_zones      = var.availability_zones
  map_public_ip_on_launch = var.map_public_ip_on_launch
  appname                 = var.appname
  env                     = var.env
}

module "instances" {
  source                 = "../../modules/instances"
  instance_type          = var.instance_type
  key_name               = var.key_name
  appname                = module.vpc.appname
  env                    = module.vpc.env
  public_subnet_ids      = module.vpc.public_subnet_ids
  security_group_id      = module.vpc.security_group_id
  public_instance_count  = module.vpc.public_cidr_blocks
}

module "eks-cluster" {
  source = "../../modules/eks"
  public_subnet_ids      = module.vpc.public_subnet_ids
  key_name               = var.key_name
  security_group_id      = module.vpc.security_group_id   
}