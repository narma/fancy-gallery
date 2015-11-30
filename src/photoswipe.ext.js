var PhotoSwipe = {};
PhotoSwipe.init = function() {};

// Go to slide by index
// @param {int} `index`
PhotoSwipe.goTo = function(index) {};
PhotoSwipe.next = function() {};
PhotoSwipe.prev = function() {};
// Update gallery size
// @param  {boolean} `force` If you set it to `true`, 
//                          size of the gallery will be updated 
//                          even if viewport size hasn't changed.
PhotoSwipe.updateSize = function(force) {};
PhotoSwipe.close = function() {};
PhotoSwipe.destroy = function() {};
PhotoSwipe.invalidateCurrItems = function() {};

// Zoom current slide to (optionally with animation)
// @param  {number}   `destZoomLevel` Destination scale number. 1 - original.  
//                                   pswp.currItem.fitRatio - image will fit into viewport.
// @param  {object}   `centerPoint`   Object of x and y coordinates, relative to viewport.
// @param  {int}      `speed`         Animation duration. Can be 0.
// @param  {function} `easingFn`      Easing function (optional). Set to false to use default easing.
// @param  {function} `updateFn`      Function will be called on each update frame. (optional)
//
// Example below will 2x zoom to center of slide:
// pswp.zoomTo(2, {x:pswp.viewportSize.x/2,y:pswp.viewportSize.y/2}, 2000, false, function(now) {
//      
// });
PhotoSwipe.zoomTo = function(destZoomLevel, centerPoint, speed, easingFn, updateFn) {};

/** Apply zoom and pan to the current slide
// 
// @param   {number} `zoomLevel`
// @param   {int}    `panX`
// @param   {int}    `panY` 
*/
PhotoSwipe.applyZoomPan = function(zoomLevel, panX, panY) {};

PhotoSwipe.currItem;
PhotoSwipe.items;
PhotoSwipe.viewportSize;
/**
 * @type {Object}
 */
PhotoSwipe.framework;

PhotoSwipe.ui;
PhotoSwipe.bg;
PhotoSwipe.container;

/**
 * @type {Object}
 */
PhotoSwipe.options;
PhotoSwipe.options.getNumItemsFn = function() {};

// @return {int}
PhotoSwipe.getCurrentIndex = function() {};
// @return {int}
PhotoSwipe.getZoomLevel = function() {};
// @return {boolean}
PhotoSwipe.isDragging = function() {};
// @return {boolean}
PhotoSwipe.isZooming = function() {};
// @return {boolean}
PhotoSwipe.isMainScrollAnimating = function() {};

// Events
PhotoSwipe.listen = function(eventName, callback) {};
PhotoSwipe.shout = function(eventName, options) {};
