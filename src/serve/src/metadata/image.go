package metadata

import (
    "image"
    "os"
    _ "image/jpeg" // required for decode jpeg images
    _ "image/png" // required for decode png images
)
    
func getImageDimension(imagePath string) (int, int, error) {
    file, err := os.Open(imagePath)
    defer file.Close()
    if err != nil {
        return 0, 0, err
    }
    
    image, _, err := image.DecodeConfig(file)
    if err != nil {
        return 0, 0, err
    }
    return image.Width, image.Height, nil
}