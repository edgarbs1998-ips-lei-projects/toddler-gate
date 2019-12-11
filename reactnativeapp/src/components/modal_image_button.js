import React, {Component} from 'react';
import {Image, TouchableOpacity, View} from 'react-native';

import styles from '../styles';

class ModalImageButton extends Component {
  render() {
    return (
      <TouchableOpacity
        activeOpacity={0.6}
        style={[styles.modalImageButton, this.props.style]}
        onPress={this.props.onPress}>
        <View style={styles.modalImageButtonView}>
          <Image
            source={this.props.source}
            style={[styles.modalImageButtonImage, this.props.imageStyle]}
          />
        </View>
      </TouchableOpacity>
    );
  }
}

export default ModalImageButton;
