library(ggplot2)
library(latex2exp)
library(ggthemes)

# aborts/s 

ms1 = read.csv("./results/aborts_delta_1.csv")
ms1$collisions = (ms1$collisions/10)
delta = rep(1,10)
ms1 = cbind(ms1,delta)
ms1 = rbind(c(100,100,0,0,0,1),ms1)

ms5 = read.csv("./results/aborts_delta_5.csv")
ms5$collisions = (ms5$collisions/10)
delta = rep(5,10)
ms5 = cbind(ms5,delta)
ms5= rbind(c(100,100,0,0,0,5),ms5)


ms10 = read.csv("./results/aborts_delta_10.csv")
ms10$collisions = (ms10$collisions/10)
delta = rep(10,10)
ms10 = cbind(ms10,delta)
ms10= rbind(c(100,100,0.01,0,0,10),ms10)


aborts = rbind(ms1,ms5,ms10)
aborts$delta = as.factor(aborts$delta)

a=ggplot(data=aborts, aes(x=tps,y=collisions,linetype=delta,color=delta)) +
    geom_line() +
     scale_linetype_discrete(name="Delta (ms): ",labels=unname(TeX(c("1","5","10")))) +
     scale_color_discrete(name="Delta (ms): ",labels=unname(TeX(c("1","5","10")))) +
     scale_x_continuous(name="TPS",breaks=seq(0,10000,1000)) +
     scale_y_continuous(name="Aborts/s",breaks=seq(0,150,25)) + 
    theme_few()
a



# load time to corruption data in 
no_isolation = read.csv("./results/no_isolation.csv")
no_isolation = rbind(c(100,4991.678),no_isolation)
type = rep(4,11)
no_isolation = cbind(no_isolation,type)

d1 = read.csv("./results/delta_1.csv")
type = rep(1,11)
d1 = cbind(d1,type)

d5 = read.csv("./results/delta_5.csv")
type = rep(2,11)
d5 = cbind(d5,type)

d10 = read.csv("./results/delta_10.csv")
type = rep(3,11)
d10 = cbind(d10,type)


df = rbind(no_isolation,d1,d5,d10)

df$type = as.factor(df$type)


png("../../paper/images/corruption_comparison.png")



p=ggplot(data=df, aes(x=lam,y=U,linetype=type,color=type)) +
    geom_line() +
    scale_linetype_discrete(name="Delta (ms): ",labels=unname(TeX(c("NA","1","5","10")))) +
    scale_color_discrete(name="Delta (ms): ",labels=unname(TeX(c("NA","1","5","10")))) +
    scale_x_continuous(name="TPS",breaks=seq(0,10000,1000),limits=c(0,10000)) +
    scale_y_continuous(name="U",breaks=seq(0,5000,500)) + 
    theme_few()

p


dev.off()



