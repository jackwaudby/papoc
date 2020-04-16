# LaTeX Makefile v0.33 -- LaTeX only

PAPER=main.tex  # set the path to your TeX file here
SHELL=/bin/zsh   # for the while loop below

all: build clean

build:
	@xelatex $(PAPER)

clean:
	@-rm -f *.aux
	@-rm -f *.out
	@-rm -f *.log
	@-rm -f *.nav
	@-rm -f *.snm
	@-rm -f *.toc
