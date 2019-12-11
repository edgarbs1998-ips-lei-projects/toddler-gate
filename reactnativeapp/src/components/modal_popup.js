import React, {Component} from 'react';
import {View, Modal, Image, TouchableOpacity} from 'react-native';

import styles from '../styles';

class ModalPopup extends Component {
  constructor(props) {
    super(props);

    this.state = {
      modalVisible: false,
    };
  }

  componentDidMount() {
    this.props.onRef(this);
  }

  componentWillUnmount() {
    this.props.onRef(undefined);
  }

  display() {
    this.setState({modalVisible: true});
  }

  close() {
    this.setState({modalVisible: false});
  }

  render() {
    return (
      <Modal
        animationType="fade"
        transparent={true}
        visible={this.state.modalVisible}>
        <View style={styles.modalPopup}>
          <View style={[styles.modalPopupView, this.props.style]}>
            {(this.props.closeButton === undefined ||
              this.props.closeButton === true) && (
              <TouchableOpacity
                activeOpacity={0.8}
                style={styles.modalCloseButton}
                onPress={() => {
                  this.setState({modalVisible: false});
                }}>
                <Image
                  source={require('../resources/images/buttons/close.png')}
                  style={styles.modalCloseButtonImage}
                />
              </TouchableOpacity>
            )}
            {this.props.children}
          </View>
        </View>
      </Modal>
    );
  }
}

export default ModalPopup;
