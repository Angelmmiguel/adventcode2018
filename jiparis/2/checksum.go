package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

func main() {

	ids := ReadValues()
	var count2, count3 int64

	for _, id := range ids {
		var freq = make(map[rune]int64)

		for _, char := range id {
			freq[char] = freq[char] + 1
		}
		fmt.Println(freq)

		var c2, c3 int64
		for _, v := range freq {
			if v == 2 {
				c2 = 1
			}
			if v == 3 {
				c3 = 1
			}
		}
		count2 += c2
		count3 += c3
	}
	fmt.Printf("count2: %d, count3: %d, checksum: %d\n", count2, count3, count2*count3)
}

func ReadValues() []string {
	file, err := os.Open("inputs.txt")
	if err != nil {
		log.Fatal("!")
	}
	defer file.Close()
	values := []string{}

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		op := scanner.Text()
		values = append(values, op)
	}
	return values
}
