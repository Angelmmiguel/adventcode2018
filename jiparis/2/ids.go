package main

import (
	"bufio"
	"bytes"
	"fmt"
	"log"
	"os"
	"sort"
)

func main() {

	ids := _ReadValues()
	sort.Strings(ids)

	for i := 0; i < len(ids); i++ {
		str1 := ids[i]
		for j := 0; j < len(ids); j++ {
			str2 := ids[j]
			res, solution := _OnlyOneDifference(str1, str2)
			if res {
				fmt.Println(solution)
				os.Exit(0)
			}
		}
	}
}

func _OnlyOneDifference(str1, str2 string) (bool, string) {
	var diffs, index int
	for pos, char := range str1 {
		if char != []rune(str2)[pos] {
			diffs++
			index = pos
		}
	}
	if diffs == 1 {
		var buffer bytes.Buffer
		buffer.WriteString(str1[0:index])
		buffer.WriteString(str1[index+1 : len(str1)])
		return true, buffer.String()
	}
	return false, ""
}

func _ReadValues() []string {
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
