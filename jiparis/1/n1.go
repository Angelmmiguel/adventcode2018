package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

func main() {
	file, err := os.Open("inputs.txt")
	if err != nil {
		log.Fatal("!")
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var value int64
	for scanner.Scan() {
		op, err := strconv.ParseInt(scanner.Text(), 10, 64)
		if err != nil {
			log.Fatal("!!")
		}
		value += op
	}
	fmt.Printf("%d\n", value)
}
