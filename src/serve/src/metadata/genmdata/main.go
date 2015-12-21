package main

import (
	"flag"
	"metadata"
	"fmt"
)

func main() {
	photosDir := flag.String("photos", "", 
			"Directory with photos where generate metadata.json")
	flag.Parse()
	
	if *photosDir == "" {
		flag.Usage()
		return	
	}
	
	bs, err := metadata.GenerateMetadata(*photosDir)
	if err != nil {
		fmt.Printf("error occured: %s\n", err)
		return
	}
	fmt.Printf("metadata: %s\n", string(bs))
}