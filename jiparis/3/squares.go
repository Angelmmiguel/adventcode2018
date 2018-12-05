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

func (c *Claim) Within(x, y int64) bool {
	return x >= c.x1 && x <= c.x2 &&
		y >= c.y1 && y <= c.y2
}

func (c1 *Claim) Intersects(c2 Claim) bool {
	return !(c1.x1 > c2.x2 ||
		c1.x2 < c2.x1 ||
		c2.y1 > c1.y2 ||
		c2.y2 < c1.y1)
}

func main() {
	var globalCount int64

	claims := []Claim{}
	inputs := _ReadValues()
	for _, input := range inputs {
		claim := _ParseClaim(input)
		claims = append(claims, claim)
	}

	for i := 0; i < 1000; i++ {
		for j := 0; j < 1000; j++ {
			var count int64
			for _, claim := range claims {
				if claim.Within(int64(i), int64(j)) {
					count++
				}
				if count == 2 {
					fmt.Printf("%d, %d\n", i, j)
					globalCount++
					break
				}
			}
		}
	}

	fmt.Printf("Count: %d\n", globalCount)
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
