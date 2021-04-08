package main

import (
	"fmt"
	"strings" // s "strings"
)

// var p = fmt.Println

func main() {
	fmt.Println(strings.ToLower("VERY IMPORTANT MESSAGE!"))
	fmt.Println(strings.ToUpper("upper"))

	fmt.Println(strings.Index("surface", "face"))
	fmt.Println(strings.Index("moon", "aer"))

	fmt.Println(strings.TrimSpace(" I don't need all this space    "))
	fmt.Println(strings.Repeat("test", 5))

	fmt.Println(strings.Replace("fooooooooooo", "oo", "t", -1)) // From the beginning to the end; replace all
	fmt.Println(strings.Replace("fooooooooooo", "o", "t", 2))   // replace only twice

	fmt.Println(strings.Split("a-b-c-d-e", "-"))
	fmt.Println(strings.Join([]string{"a", "b"}, "-"))

	fmt.Println(strings.Contains("test", "es"))
	fmt.Println(strings.Count("test", "t"))
	fmt.Println(strings.HasPrefix("test", "te"))
	fmt.Println(strings.HasSuffix("test", "st"))

	fmt.Println(len("Hello"))
	fmt.Println("Hello"[1])
}
