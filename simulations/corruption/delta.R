## command line args
args = commandArgs(trailingOnly=TRUE)
eps = as.numeric(args[1])

source(file= "./simulations/corruption/calculate_delta.R")

calc_d(eps=eps,del=200)

