default: noop

noop:
	echo 'exit'

thumbs:
	mkdir -p photos/thumbs
	rm -rf "photos/thumbs/*"
	cd photos && mogrify -path thumbs -define jpeg:size=1000x\
	 -thumbnail '300x300^' -quality 75 -gravity center -extent 300x300 '*.jpg'

resize:
	./scripts/resize.sh 

.PHONY: thumbs resize
