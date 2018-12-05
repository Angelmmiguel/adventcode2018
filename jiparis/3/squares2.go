package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"regexp"
	"strconv"
)

type Claim struct {
	ID             string
	x1, x2, y1, y2 int64
}

func (c1 *Claim) Intersects(c2 Claim) bool {
	return c1.x1 <= c2.x2 &&
		c1.x2 >= c2.x1 &&
		c1.y1 <= c2.y2 &&
		c1.y2 >= c2.y1
}

func main() {
	var count int
	claims := []Claim{}
	inputs := _ReadValues()
	for _, input := range inputs {
		claim := _ParseClaim(input)
		claims = append(claims, claim)
	}

	for _, c1 := range claims {
		intersects := false
		for _, c2 := range claims {
			if c1.ID == c2.ID {
				continue
			}
			if c1.Intersects(c2) {
				fmt.Printf("Collision! %s, %s\n", c1.ID, c2.ID)
				count++
				intersects = true
				break
			}
		}
		if intersects == false {
			fmt.Printf("I have a Santa's suit!: #%s\n", c1.ID)
			os.Exit(0)
		}
	}
	fmt.Println(count)
}

func _ParseClaim(line string) Claim {
	r := regexp.MustCompile(`#(?P<ID>\d*) @ (?P<left>\d*),(?P<top>\d*): (?P<width>\d*)x(?P<height>\d*)`)
	match := r.FindStringSubmatch(line)
	id := match[1]
	x1, _ := strconv.ParseInt(match[2], 10, 64)
	y1, _ := strconv.ParseInt(match[3], 10, 64)
	w, _ := strconv.ParseInt(match[4], 10, 64)
	h, _ := strconv.ParseInt(match[5], 10, 64)
	x2 := x1 + w - 1
	y2 := y1 + h - 1
	return Claim{id, x1, x2, y1, y2}
}

func _ReadValues() []string {
	file, err := os.Open("claims.txt")
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
