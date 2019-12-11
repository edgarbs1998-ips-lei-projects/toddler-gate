import React, {Component} from 'react';
import {Image, TouchableOpacity} from 'react-native';

import styles from '../styles';

class MenuButton extends Component {
  render() {
    return (
      <TouchableOpacity
        activeOpacity={0.8}
        style={styles.menuButton}
        onPress={this.props.onPress}>
        <Image source={this.props.source} style={styles.menuButtonImage} />
      </TouchableOpacity>
    );
  }
}

export default MenuButton;
