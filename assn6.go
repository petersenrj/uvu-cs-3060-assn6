/* Promise of Originality
I promise that this source code file has, in it's entirety, been
written by myself or a member of my group and by no other person or
persons. If at any time an exact copy of this source code is found to
be used by another person outside my group in this term, I understand
that all members of my group and the members of the group that
submitted the copy will receive a zero on this assignment.
*/

package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"os/exec"
	"runtime/debug"
	"sort"
	"strconv"
	"strings"
	"time"
)

var filepath string
var tmpFile = false
var blocks = []int{}

func main() {
	parseInput()
	defer cleanup()
	defer func() {
		if r := recover(); r != nil {
			log.Printf("Recover - %s: %s", r, string(debug.Stack()))
		}
	}()
	fcfs()
	runJava("ssft.jar")
	look()
	runJava("clook.jar")
}

func runJava(jar string) {
	javaOut, err := exec.Command("java", "-jar", jar, filepath).Output()
	if err != nil {
		log.Println("Error starting", jar, err)
	}
	fmt.Print(string(javaOut))
}

func fcfs() {
	total := 0
	loc := blocks[:1][0]
	for _, b := range blocks[1:] {
		tmp := loc - b
		if tmp < 0 {
			tmp = -tmp
		}
		total += tmp
		loc = b
	}
	fmt.Println("FCFS Total Seek:", total)
}

func look() {
	loc := blocks[:1][0]
	greater := []int{}
	smaller := []int{}
	for _, i := range blocks[1:] {
		if i >= loc {
			greater = append(greater, i)
		} else {
			smaller = append(smaller, i)
		}
	}
	sort.Ints(greater)
	sort.Sort(sort.Reverse(sort.IntSlice(smaller)))
	total := 0
	for _, b := range greater {
		total += b - loc
		loc = b
	}
	for _, b := range smaller {
		total += loc - b
		loc = b
	}
	fmt.Println("LOOK Total Seek:", total)
}

func parseInput() {
	bio := bufio.NewReader(os.Stdin)
	stat, _ := os.Stdin.Stat()
	if (stat.Mode() & os.ModeCharDevice) != 0 {
		filepath = os.Args[len(os.Args)-1:][0]
		f, err := os.Open(filepath)
		if err != nil {
			log.Fatalf("Error opening file '%s' %q", filepath, err)
		}
		defer f.Close()
		bio = bufio.NewReader(f)
	} else {
		tmpFile = true
		filepath = "tmp" + strconv.FormatInt(time.Now().Unix(), 10) + ".txt"
	}
	content := ""
	for {
		line, err := bio.ReadString('\n')
		if err != nil {
			break
		}
		parseLine(line)
		if tmpFile {
			content += line
		}
	}
	if tmpFile {
		f, err := os.Create(filepath)
		if err != nil {
			log.Fatalln("Could not create file:", err)
		}
		defer f.Close()
		_, err = f.Write([]byte(content))
		if err != nil {
			log.Fatalln("Could not write content to file:", err)
		}
	}
}

func parseLine(line string) {
	line = strings.Trim(line, "\n")
	i, err := strconv.Atoi(line)
	if err != nil {
		log.Fatalf("Counld not parse line '%s'", line)
	}
	blocks = append(blocks, i)
}

func cleanup() {
	if tmpFile {
		err := os.Remove(filepath)
		if err != nil {
			log.Println("Could not delete tmp file:", err)
			return
		}
	}
}
