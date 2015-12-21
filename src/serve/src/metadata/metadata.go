package metadata

import (
	edn "gopkg.in/edn.v1"
	"io/ioutil"
	"path/filepath"
)

// ImageMeta contains info about image: name, width, height
type ImageMeta struct {
    Name      string
    Width 	  int `edn:"w"`
    Height    int `edn:"h"`  
}

// Metadata is EDN format which includes all required info about directory with
// photos. Used at UI. 
type Metadata []ImageMeta

var images = []string{".jpg", ".jpeg", ".png"}

// GenerateMetadata returns serialized metadata for specified dir
func GenerateMetadata(dir string) ([]byte, error) {
	var m Metadata
	
	files, err := ioutil.ReadDir(dir)
	if err != nil {
		return nil, err
	}
	
	for _, f := range files {
		if f.IsDir() {
			continue
		}
		name := f.Name()
		if !stringInSlice(filepath.Ext(name), images) {
			continue
		}
		abspath := filepath.Join(dir, name)
		width, height, err := getImageDimension(abspath)
		if err != nil {
			return nil, err
		}
		m = append(m, ImageMeta{Name: name, Width: width, Height: height})
	}
	bs, err := edn.Marshal(m)
	if err != nil {
		return nil, err
	}
	return bs, nil
}

func stringInSlice(a string, list []string) bool {
    for _, b := range list {
        if b == a {
            return true
        }
    }
    return false
}