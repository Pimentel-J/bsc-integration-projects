prog: main.o
	gcc main.o -o prog -lrt -lpthread -lssl -lcrypto

main.o: main.c
	gcc -g -Wall -c main.c -o main.o -lrt -lpthread -lssl -lcrypto

clean:
	rm -f *.o prog

run: prog
	./prog 202 0
