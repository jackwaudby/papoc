library(ggplot2)
library(latex2exp)
# load time to corruption data in 
no_isolation = read.csv("../../results/corruption/no_isolation.csv")
type = rep(1,14)
no_isolation = cbind(no_isolation,type)

e01 = read.csv("../../results/corruption/probabilistic_0_1.csv")
type = rep(2,14)
e01 = cbind(e01,type)

e005 = read.csv("../../results/corruption/probabilistic_0_05.csv")
type = rep(3,14)
e005 = cbind(e005,type)

e001 = read.csv("../../results/corruption/probabilistic_0_01.csv")
type = rep(4,14)
e001 = cbind(e001,type)


df = rbind(no_isolation,e005, e01,e001)

df$type = as.factor(df$type)


png("../paper/images/corruption_comparison.png")



p=ggplot(data=df, aes(x=lam,y=U,linetype=type,color=type)) +
    geom_line() +
    scale_linetype_discrete(name="Type: ",labels=unname(TeX(c(
      '$\\epsilon = 100%$',
      "$\\epsilon$ = 10%",
      "$\\epsilon$ = 5%",
      "$\\epsilon$ = 1%")))) +
    scale_color_discrete(name="Type: ",labels=unname(TeX(c(
      '$\\epsilon = 100%$',
      "$\\epsilon$ = 10%",
      "$\\epsilon$ = 5%",
      "$\\epsilon$ = 1%")))) +
    scale_x_continuous(name=unname(TeX(c("Read-Write Transactions per second (10% TPS)"))),
                       breaks=seq(100,1000,100),limits=c(0,1000)) +
    scale_y_continuous(name=unname(TeX(c("Time (in hours) for 10% DB Corruption"))),
                       breaks=seq(0,5000,500)) +
    theme(legend.text.align = 0)

p


dev.off()



