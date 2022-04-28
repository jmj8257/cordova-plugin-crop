# Important

## This project is deprecated. No new changes nor bugfixes will be added.


# cordova-plugin-crop



> Crop an image in a Cordova app


## Install

```
$ cordova plugin add https://github.com/jmj8257/cordova-plugin-crop.git --save
```


## Usage

```js
plugins.crop(function success () {

}, function fail () {

}, '/path/to/image', options)
```

or, if you are running on an environment that supports Promises
(Crosswalk, Android >= KitKat, iOS >= 8)

```js
plugins.crop.promise('/path/to/image', options)
.then(function success (newPath) {

})
.catch(function fail (err) {

})
```

## API

 * quality: Number - The resulting JPEG quality (ignored on Android). [default: 100]
 * targetWidth: Number - The resulting JPEG picture width. [default: -1]
 * targetHeight: Number - The resulting JPEG picture height. [default: -1]
 * square: Boolean - 잘라내기 영역 정사각형 고정. [default: false]


### Libraries used

 * iOS: [PEPhotoCropEditor](https://github.com/kishikawakatsumi/PEPhotoCropEditor)
 * Android: [android-crop](https://github.com/ArthurHub/Android-Image-Cropper)

## License

MIT © [jmj8257 Cornejo](https://github.com/jmj8257)
