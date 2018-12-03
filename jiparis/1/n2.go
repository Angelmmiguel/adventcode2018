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
	values := []int64{}

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		op, err := strconv.ParseInt(scanner.Text(), 10, 64)
		if err != nil {
			log.Fatal("!!")
		}
		values = append(values, op)
	}

	freqs := []int64{}
	var value int64

	for true {
		for _, n := range values {
			value += n
			fmt.Println(value)
			for _, f := range freqs {
				if value == f {
					fmt.Printf("Repeated! %d\n", value)
					return
				}
			}
			freqs = append(freqs, value)
		}
	}
}
