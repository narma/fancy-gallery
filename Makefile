default: noop

hoop:
	echo 'exit'

thumbs:
	mkdir -p photos/thumbs
	cd photos && mogrify -path thumbs -define jpeg:size=1000x\
	 -thumbnail '300x300^' -gravity center -extent 300x300 '*.jpg'

.PHONY: thumbs
