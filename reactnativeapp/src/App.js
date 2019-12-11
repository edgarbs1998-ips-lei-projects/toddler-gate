/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

'use strict';

import React, {Component} from 'react';
import {
  ImageBackground,
  View,
  BackHandler,
  ScrollView,
  Dimensions,
  Image,
} from 'react-native';
import ScalableImage from 'react-native-scalable-image';
import Sound from 'react-native-sound';

import MenuButton from './components/menu_button';
import ModalPopup from './components/modal_popup';
import ModalImageButton from './components/modal_image_button';

import styles from './styles';

export default class App extends Component {
  constructor(props) {
    super(props);

    this.bacgrkounds = {
      bg1: require('./resources/images/backgrounds/background1.jpg'),
      bg2: require('./resources/images/backgrounds/background2.jpg'),
      bg3: require('./resources/images/backgrounds/background3.jpg'),
      bg4: require('./resources/images/backgrounds/background4.jpg'),
      bg5: require('./resources/images/backgrounds/background5.jpg'),
      bg6: require('./resources/images/backgrounds/background6.jpg'),
    };

    this.pianoKeys = {
      white: require('./resources/images/piano/key_white.png'),
      whitePressed: require('./resources/images/piano/key_white_pressed.png'),
      black: require('./resources/images/piano/key_black.png'),
    };

    this.pianoSounds = {
      1: {
        1: new Sound(require('./resources/sounds/piano/c.mp3'), ''),
        2: new Sound(require('./resources/sounds/piano/d.mp3'), ''),
        3: new Sound(require('./resources/sounds/piano/e.mp3'), ''),
        4: new Sound(require('./resources/sounds/piano/f.mp3'), ''),
        5: new Sound(require('./resources/sounds/piano/g.mp3'), ''),
        6: new Sound(require('./resources/sounds/piano/a.mp3'), ''),
        7: new Sound(require('./resources/sounds/piano/b.mp3'), ''),
        8: new Sound(require('./resources/sounds/piano/cs.mp3'), ''),
      },
      2: {
        1: new Sound(require('./resources/sounds/animals/dog.mp3'), ''),
        2: new Sound(require('./resources/sounds/animals/cat.mp3'), ''),
        3: new Sound(require('./resources/sounds/animals/cow.mp3'), ''),
        4: new Sound(require('./resources/sounds/animals/sheep.mp3'), ''),
        5: new Sound(require('./resources/sounds/animals/horse.mp3'), ''),
        6: new Sound(require('./resources/sounds/animals/cock.mp3'), ''),
        7: new Sound(require('./resources/sounds/animals/elephant.mp3'), ''),
        8: new Sound(require('./resources/sounds/animals/monkey.mp3'), ''),
      },
      3: {
        1: new Sound(
          require('./resources/sounds/instruments/acoustic_guitar.mp3'),
          '',
        ),
        2: new Sound(require('./resources/sounds/instruments/bongo.mp3'), ''),
        3: new Sound(
          require('./resources/sounds/instruments/clarinet.mp3'),
          '',
        ),
        4: new Sound(require('./resources/sounds/instruments/drums.mp3'), ''),
        5: new Sound(require('./resources/sounds/instruments/flute.mp3'), ''),
        6: new Sound(
          require('./resources/sounds/instruments/harmonica.mp3'),
          '',
        ),
        7: new Sound(
          require('./resources/sounds/instruments/trombone.mp3'),
          '',
        ),
        8: new Sound(require('./resources/sounds/instruments/trumpet.mp3'), ''),
      },
    };

    this.state = {
      backHandler: null,

      backgroundImage: this.bacgrkounds.bg1,
      soundCategory: 1,

      pianoKey: {
        1: this.pianoKeys.white,
        2: this.pianoKeys.white,
        3: this.pianoKeys.white,
        4: this.pianoKeys.white,
        5: this.pianoKeys.white,
        6: this.pianoKeys.white,
        7: this.pianoKeys.white,
        8: this.pianoKeys.white,
      },
    };
  }

  componentDidMount() {
    if (this.backHandler) {
      this.backHandler.remove();
    }
    this.backHandler = BackHandler.addEventListener('backPress', () => {
      this.modalExit.display();
      return true;
    });
  }

  componentWillUnmount() {
    if (this.backHandler) {
      this.backHandler.remove();
    }

    if (this.pianoSounds) {
      for (let category of Object.values(this.pianoSounds)) {
        for (let sound of Object.values(category)) {
          if (sound) {
            sound.release();
            sound = undefined;
          }
        }
      }
    }
  }

  setBackgroundImage(id) {
    switch (id) {
      case 1:
        this.setState({backgroundImage: this.bacgrkounds.bg1});
        break;
      case 2:
        this.setState({backgroundImage: this.bacgrkounds.bg2});
        break;
      case 3:
        this.setState({backgroundImage: this.bacgrkounds.bg3});
        break;
      case 4:
        this.setState({backgroundImage: this.bacgrkounds.bg4});
        break;
      case 5:
        this.setState({backgroundImage: this.bacgrkounds.bg5});
        break;
      case 6:
        this.setState({backgroundImage: this.bacgrkounds.bg6});
        break;
    }
  }

  startPianoKey(keyId) {
    let pianoKeyState = this.state.pianoKey;
    pianoKeyState[keyId] = this.pianoKeys.whitePressed;
    this.setState({pianoKeyImage: pianoKeyState});

    // play sound
    this.pianoSounds[this.state.soundCategory][keyId].stop(() => {
      this.pianoSounds[this.state.soundCategory][keyId].setVolume(1.0);
      this.pianoSounds[this.state.soundCategory][keyId].play();
    });
  }

  stopPianoKey(keyId) {
    let pianoKeyState = this.state.pianoKey;
    pianoKeyState[keyId] = this.pianoKeys.white;
    this.setState({pianoKeyImage: pianoKeyState});
  }

  stopAllSound() {
    for (let category of Object.values(this.pianoSounds)) {
      for (let sound of Object.values(category)) {
        sound.stop();
      }
    }
  }

  render() {
    return (
      <ImageBackground
        source={this.state.backgroundImage}
        style={styles.backgroundImage}
        resizeMode="stretch">
        <View style={styles.topBar}>
          <View style={styles.topBarLeft}>
            <MenuButton
              source={require('./resources/images/buttons/exit.png')}
              onPress={() => {
                this.modalExit.display();
              }}
            />
          </View>
          {/*<View style={styles.topBarCenter}>*/}
          {/*  <MenuButton*/}
          {/*    source={require('./resources/images/buttons/record.png')}*/}
          {/*  />*/}
          {/*</View>*/}
          <View style={styles.topBarRight}>
            <MenuButton
              source={require('./resources/images/buttons/change_background.png')}
              onPress={() => {
                this.modalBackground.display();
              }}
            />
            <MenuButton
              source={require('./resources/images/buttons/change_sound.png')}
              onPress={() => {
                this.modalSound.display();
              }}
            />
          </View>
        </View>

        <View style={styles.pianoView}>
          <View>
            <ScalableImage
              source={require('./resources/images/piano/piano.png')}
              width={Dimensions.get('window').width * 0.9}
            />
            <View style={styles.pianoKeysView}>
              <Image
                source={this.state.pianoKey[1]}
                style={styles.pianoWhiteKey}
                onTouchStart={() => {
                  this.startPianoKey(1);
                }}
                onTouchEnd={() => {
                  this.stopPianoKey(1);
                }}
              />
              <Image
                source={this.state.pianoKey[2]}
                style={styles.pianoWhiteKey}
                onTouchStart={() => {
                  this.startPianoKey(2);
                }}
                onTouchEnd={() => {
                  this.stopPianoKey(2);
                }}
              />
              <Image
                source={this.state.pianoKey[3]}
                style={styles.pianoWhiteKey}
                onTouchStart={() => {
                  this.startPianoKey(3);
                }}
                onTouchEnd={() => {
                  this.stopPianoKey(3);
                }}
              />
              <Image
                source={this.state.pianoKey[4]}
                style={styles.pianoWhiteKey}
                onTouchStart={() => {
                  this.startPianoKey(4);
                }}
                onTouchEnd={() => {
                  this.stopPianoKey(4);
                }}
              />
              <Image
                source={this.state.pianoKey[5]}
                style={styles.pianoWhiteKey}
                onTouchStart={() => {
                  this.startPianoKey(5);
                }}
                onTouchEnd={() => {
                  this.stopPianoKey(5);
                }}
              />
              <Image
                source={this.state.pianoKey[6]}
                style={styles.pianoWhiteKey}
                onTouchStart={() => {
                  this.startPianoKey(6);
                }}
                onTouchEnd={() => {
                  this.stopPianoKey(6);
                }}
              />
              <Image
                source={this.state.pianoKey[7]}
                style={styles.pianoWhiteKey}
                onTouchStart={() => {
                  this.startPianoKey(7);
                }}
                onTouchEnd={() => {
                  this.stopPianoKey(7);
                }}
              />
              <Image
                source={this.state.pianoKey[8]}
                style={styles.pianoWhiteKey}
                onTouchStart={() => {
                  this.startPianoKey(8);
                }}
                onTouchEnd={() => {
                  this.stopPianoKey(8);
                }}
              />
            </View>
            <View style={styles.pianoKeysView}>
              <Image
                source={this.pianoKeys.black}
                style={styles.pianoBlackKey}
              />
              <Image
                source={this.pianoKeys.black}
                style={styles.pianoBlackKeyNext}
              />
              <Image
                source={this.pianoKeys.black}
                style={styles.pianoBlackKeyDoubleNext}
              />
              <Image
                source={this.pianoKeys.black}
                style={styles.pianoBlackKeyNext}
              />
              <Image
                source={this.pianoKeys.black}
                style={styles.pianoBlackKeyNext}
              />
            </View>
          </View>
        </View>

        <ModalPopup onRef={ref => (this.modalExit = ref)} closeButton={false}>
          <View style={{flexDirection: 'row'}}>
            <ModalImageButton
              source={require('./resources/images/buttons/exit_button_no.png')}
              onPress={() => {
                this.modalExit.close();
              }}
            />
            <ModalImageButton
              source={require('./resources/images/buttons/exit_button_yes.png')}
              onPress={() => {
                BackHandler.exitApp();
              }}
            />
          </View>
        </ModalPopup>

        <ModalPopup
          onRef={ref => (this.modalBackground = ref)}
          style={{width: '80%', height: '80%'}}>
          <ScrollView>
            <View style={styles.modalBackground}>
              <ModalImageButton
                source={require('./resources/images/backgrounds/background1.jpg')}
                style={styles.modalBackgroundButton}
                imageStyle={styles.modalImageButtonFullImage}
                onPress={() => {
                  this.setBackgroundImage(1);
                  //AsyncStorage.setItem('backgroundImageId', 1);
                  this.modalBackground.close();
                }}
              />
              <ModalImageButton
                source={require('./resources/images/backgrounds/background2.jpg')}
                style={styles.modalBackgroundButton}
                imageStyle={styles.modalImageButtonFullImage}
                onPress={() => {
                  this.setBackgroundImage(2);
                  //AsyncStorage.setItem('backgroundImageId', 2);
                  this.modalBackground.close();
                }}
              />
              <ModalImageButton
                source={require('./resources/images/backgrounds/background3.jpg')}
                style={styles.modalBackgroundButton}
                imageStyle={styles.modalImageButtonFullImage}
                onPress={() => {
                  this.setBackgroundImage(3);
                  this.modalBackground.close();
                }}
              />
              <ModalImageButton
                source={require('./resources/images/backgrounds/background4.jpg')}
                style={styles.modalBackgroundButton}
                imageStyle={styles.modalImageButtonFullImage}
                onPress={() => {
                  this.setBackgroundImage(4);
                  this.modalBackground.close();
                }}
              />
              <ModalImageButton
                source={require('./resources/images/backgrounds/background5.jpg')}
                style={styles.modalBackgroundButton}
                imageStyle={styles.modalImageButtonFullImage}
                onPress={() => {
                  this.setBackgroundImage(5);
                  this.modalBackground.close();
                }}
              />
              <ModalImageButton
                source={require('./resources/images/backgrounds/background6.jpg')}
                style={styles.modalBackgroundButton}
                imageStyle={styles.modalImageButtonFullImage}
                onPress={() => {
                  this.setBackgroundImage(6);
                  this.modalBackground.close();
                }}
              />
            </View>
          </ScrollView>
        </ModalPopup>

        <ModalPopup onRef={ref => (this.modalSound = ref)}>
          <View style={{flexDirection: 'row'}}>
            <ModalImageButton
              source={require('./resources/images/buttons/sound_piano.png')}
              onPress={() => {
                this.stopAllSound();
                this.setState({soundCategory: 1});
                this.modalSound.close();
              }}
            />
            <ModalImageButton
              source={require('./resources/images/buttons/sound_animals.png')}
              onPress={() => {
                this.stopAllSound();
                this.setState({soundCategory: 2});
                this.modalSound.close();
              }}
            />
            <ModalImageButton
              source={require('./resources/images/buttons/sound_instruments.png')}
              onPress={() => {
                this.stopAllSound();
                this.setState({soundCategory: 3});
                this.modalSound.close();
              }}
            />
          </View>
        </ModalPopup>
      </ImageBackground>
    );
  }
}
