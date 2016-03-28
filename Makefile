assn6: assn6.go Makefile
	javac ssft.java clook.java
	@#Create Jar files
	jar cfe ssft.jar ssft ssft.java ssft.class
	jar cfe clook.jar clook clook.java clook.class
	@rm ssft.class clook.class
	go build -o assn6 ./

lint: assn6.go
	@if [ `hash golint 2>/dev/null` ]; then golint *.go; fi
	gofmt -w ./

cleanup:
	rm -f assn6 ssft.jar clook.jar

test:
	make lint
	make assn6; echo
	./assn6 block-list.txt; echo
	cat block-list.txt | ./assn6
	@make cleanup
