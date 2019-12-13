import {StyleSheet} from 'react-native';

const styles = StyleSheet.create({
  backgroundImage: {
    width: '100%',
    height: '100%',
  },
  menuButton: {
    margin: 4,
    justifyContent: 'center',
    alignItems: 'center',
    width: 48,
    height: 48,
    borderStyle: 'solid',
    borderRadius: 10,
    borderWidth: 4,
    borderColor: '#975925',
    backgroundColor: '#c68238',
    elevation: 5,
  },
  menuButtonImage: {
    width: '80%',
    height: '80%',
  },
  topBarLeft: {
    flex: 1,
    flexDirection: 'row',
  },
  topBar: {
    flexDirection: 'row',
  },
  topBarCenter: {
    flex: 6,
    flexDirection: 'row',
    justifyContent: 'flex-end',
  },
  topBarRight: {
    flex: 2,
    flexDirection: 'row',
    justifyContent: 'flex-end',
  },
  modalPopup: {
    backgroundColor: '#00000066',
    height: '100%',
    width: '100%',
    alignItems: 'center',
    justifyContent: 'center',
  },
  modalPopupView: {
    borderStyle: 'solid',
    borderRadius: 10,
    borderWidth: 8,
    borderColor: '#975925',
    backgroundColor: '#c68238',
    alignItems: 'center',
    justifyContent: 'center',
  },
  modalImageButton: {
    margin: 12,
    justifyContent: 'center',
    alignItems: 'center',
    width: 96,
    height: 96,
    borderStyle: 'solid',
    borderRadius: 10,
    borderWidth: 3,
    borderColor: '#975925',
    backgroundColor: '#FFB361',
    elevation: 5,
  },
  modalImageButtonView: {
    alignItems: 'center',
    justifyContent: 'center',
    width: '95%',
    height: '95%',
    borderStyle: 'solid',
    borderRadius: 5,
    borderWidth: 2,
    borderColor: '#975925',
    backgroundColor: '#FFB361',
  },
  modalImageButtonImage: {
    width: '85%',
    height: '85%',
  },
  modalImageButtonFullImage: {
    width: '100%',
    height: '100%',
    borderRadius: 3,
  },
  modalCloseButton: {
    position: 'relative',
    marginLeft: 'auto',
    marginBottom: -36,
    right: -18,
    top: -18,
    zIndex: 1,
  },
  modalCloseButtonImage: {
    width: 36,
    height: 36,
  },
  modalBackground: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    justifyContent: 'space-evenly',
    flexWrap: 'wrap',
  },
  modalBackgroundButton: {
    width: 240,
    height: 135,
  },
  pianoView: {
    flex: 1,
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  pianoKeysView: {
    flexDirection: 'row',
    height: '42%',
    position: 'absolute',
    top: '10%',
    left: '4%',
    right: '4%',
  },
  pianoWhiteKey: {
    width: '12.5%',
    height: '100%',
  },
  pianoBlackKey: {
    width: '3%',
    height: '60%',
    marginLeft: '11%',
  },
  pianoBlackKeyNext: {
    width: '3%',
    height: '60%',
    marginLeft: '9.5%',
  },
  pianoBlackKeyDoubleNext: {
    width: '3%',
    height: '60%',
    marginLeft: '22%',
  },
});

module.exports = styles;
