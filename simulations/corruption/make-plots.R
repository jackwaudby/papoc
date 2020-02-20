library(ggplot2)
library(latex2exp)
library(ggthemes)

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


png("../../paper/images/corruption_comparison.png")



p=ggplot(data=df, aes(x=lam,y=U,linetype=type,color=type)) +
    geom_line() +
    scale_linetype_discrete(name="Delta: ",labels=unname(TeX(c("2.3s","1.5s","1.2s","0s")))) +
    scale_color_discrete(name="Delta: ",labels=unname(TeX(c("2.3s","1.5s","1.2s","0s")))) +
    scale_x_continuous(name="TPS",breaks=seq(0,2000,200),limits=c(0,2000)) +
    scale_y_continuous(name="U",breaks=seq(0,5000,500)) + 
    theme_few()

p


dev.off()



