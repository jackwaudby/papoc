# LaTeX Makefile v0.33 -- LaTeX only

PAPER=main.tex  # set the path to your TeX file here
SHELL=/bin/zsh   # for the while loop below

all: build clean

build:
	@latexmk -pdf -bibtex $(PAPER)

clean:
	@-rm -f *.aux
	@-rm -f *.out
	@-rm -f *.log
	@-rm -f *.fdb_latexmk
	@-rm -f *.fls
