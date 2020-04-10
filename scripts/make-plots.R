library(ggplot2)
library(latex2exp)
library(ggthemes)

# aborts/s 
ms50 = read.csv("./results/aborts_50.csv")
ms50$collisions = (ms50$collisions/ms50$arrivals)
delta = rep(50,11)
ms50 = cbind(ms50,delta)

ms75 = read.csv("./results/aborts_75.csv")
ms75$collisions = (ms75$collisions/ms75$arrivals)
delta = rep(75,11)
ms75 = cbind(ms75,delta)

ms100 = read.csv("./results/aborts_100.csv")
ms100$collisions = (ms100$collisions/ms100$arrivals)
delta = rep(100,11)
ms100 = cbind(ms100,delta)

aborts = rbind(ms50,ms75,ms100)
aborts$delta = as.factor(aborts$delta)

a=ggplot(data=aborts, aes(x=tps,y=collisions,linetype=delta,color=delta)) +
    geom_line() +
     scale_linetype_discrete(name=TeX('$\\Delta$ (ms):'),labels=unname(TeX(c("50","75","100")))) +
     scale_color_discrete(name=TeX('$\\Delta$ (ms):'),labels=unname(TeX(c("50","75","100")))) +
     scale_x_continuous(name=TeX('$\\lambda$ (TPS)'),breaks=seq(0,10000,1000)) +
     scale_y_continuous(name="Abort probability",breaks=seq(0,0.1,0.01)) + 
    theme_few()
a

ggsave(a, filename = "./paper/figures/aborts.pdf",  bg = "transparent",width = 6.5, height = 4.5)

# corruption
no_isolation = read.csv("./results/no_isolation.csv")
type = rep(1,10)
no_isolation = cbind(no_isolation,type)
 
d1 = read.csv("./results/delta_50.csv")
d1 = d1[-1,]
type = rep(2,5)
d1 = cbind(d1,type)

d5 = read.csv("./results/delta_75.csv")
d5 = d5[-1,]
type = rep(3,5)
d5 = cbind(d5,type)

d10 = read.csv("./results/delta_100.csv")
d10 = d10[-1,]
type = rep(4,5)
d10 = cbind(d10,type)

df = rbind(no_isolation,d1[,c(1,2,4)],d5[,c(1,2,4)],d10[,c(1,2,4)])
df$U = log(df$U)
df$type = as.factor(df$type)

p=ggplot(data=df, aes(x=lam,y=U,linetype=type,color=type)) +
    geom_line() +
    scale_linetype_discrete(name=TeX('$\\Delta$ (ms):'),labels=unname(TeX(c("NA","50","75","100")))) +
    scale_color_discrete(name=TeX('$\\Delta$ (ms):'),labels=unname(TeX(c("NA","50","75","100")))) +
    scale_x_continuous(name=TeX('$\\lambda$ (TPS)'),breaks=seq(0,10000,1000),limits=c(0,10000)) +
    scale_y_continuous(name="log(U)",breaks=seq(0,40,5)) + 
    theme_few()
p
ggsave(p, filename = "./paper/figures/delta.pdf",  bg = "transparent",width = 6.5, height = 4.5)




