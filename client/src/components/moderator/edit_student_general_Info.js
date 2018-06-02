import React, {Component} from "react"
import {bindActionCreators} from "redux"
import {connect} from "react-redux"
import {message} from "../../locale/message"
import {
  ACCEPTED,
  REJECTED
} from "../../constants"

import {fetchStudentPersonalInfoByModerator, editGeneralInfo} from "../../actions";

class EditStudentGeneralInfo extends Component {

  constructor(props) {
    super(props)

    this.state = {
      commentState: this.props.personalInfo.comment || ""
    }
  }

  componentDidMount() {
    const {currentStudentId, fetchStudentPersonalInfoByModerator} = this.props
    fetchStudentPersonalInfoByModerator(currentStudentId,
      () => {
        this.setState({commentState: this.props.personalInfo.comment})
        console.log('success on fetchStudentPersonalInfoByModerator')
      },
      () => {
        console.log('fail on fetchStudentPersonalInfoByModerator')
      }
    )
  }

  onGeneralInfoEdit = (status) => {
    const {editGeneralInfo, currentStudentId} = this.props

    editGeneralInfo(currentStudentId, this.state.commentState, status,
      () => {
        console.log('success 123')
      },
      () => {
        console.log('failure 123')
      })
  }

  onTextAreaChange = (event) => {
    this.setState({commentState: event.target.value})
  }

  render() {
    const {currentStudentId, lang, personalInfo} = this.props

    const {firstName, middleName, lastName, gender, birthDate, givenDate, givenPlace, iin, ud_number, mobilePhone,
      telPhone, nationality, birthPlace, blood_group, citizenship, factStreet, factHouse, factFraction, factFlat, regStreet, regHouse, regFraction,
      regFlact, ud_front, ud_back, photo3x4, comment, status} = personalInfo

    // console.log('current student id', currentStudentId)
    // console.log('personal info ', personalInfo)
    // const comment = this.state.comment
    const com = personalInfo.comment
    console.log('com ', com)
    const commentState = this.state.commentState


    const acceptBtnClass = "btn " + (status === "VALID" && comment === commentState ? "btn-success" : "btn-outline-success")
    const rejectBtnClass = "btn ml-2 " + (status === "INVALID" && comment === commentState ? "btn-danger" : "btn-outline-danger")

    if(birthPlace) {
      console.log('birthPlace ', birthPlace)
    }
    let birthPlaceLabel = ""
    if(birthPlace) {
      birthPlaceLabel = birthPlace.nameEn
    }
    return (
      <div className="row mt-5">
        <div className="col-md-6">
          <p>{message.first_name[lang]} : {firstName}</p>
          <p>{message.patronymic[lang]} : {middleName}</p>
          <p>{message.last_name[lang]} : {lastName}</p>
          <p>{message.birthDate[lang]} : {birthDate}</p>
          <p>{message.iin[lang]} : {iin}</p>

          <p>{message.birthPlace[lang]} : {birthPlaceLabel}</p>
          <p>{message.nationality[lang]} : {nationality}</p>
          <p>{message.givenPlace[lang]} : {givenPlace}</p>
          <p>{message.givenDate[lang]} : {givenDate}</p>
          <p>{message.ud_number[lang]} : {ud_number}</p>

          <p>{message.gender[lang]} : {gender}</p>
          <p>{message.citizenship[lang]} : {citizenship}</p>
          <p>{message.blood_group[lang]} : {blood_group}</p>
          <p>{message.tel_phone[lang]} : {telPhone}</p>
          <p>{message.mobile_phone[lang]} : {mobilePhone}</p>

          <h5 className="mt-3">{message.registeredAddress[lang]}</h5>
          <p>{message.street[lang]} : {regStreet}</p>
          <p>{message.house[lang]} : {regHouse}</p>
          <p>{message.fraction[lang]} : {regFraction}</p>
          <p>{message.flat[lang]} : {regFlact}</p>

          <h5 className="mt-3">{message.factAddress[lang]}</h5>
          <p>{message.street[lang]} : {factStreet}</p>
          <p>{message.house[lang]} : {factHouse}</p>
          <p>{message.fraction[lang]} : {factFraction}</p>
          <p>{message.flat[lang]} : {factFlat}</p>
        </div>
        <div className="col-md-6">
          <div className="mt-5 pb-3 border-bottom-black">
            {!photo3x4 && <p className="text-danger">Photo 3x4 is not uploaded</p>}
            {photo3x4 && <a href={`http://localhost:8081/api/upload/${photo3x4}`} target="_blank" className="d-block"><img src={`http://localhost:8081/api/upload/${photo3x4}`} className="img-fluid" alt="Responsive image"/></a>}
          </div>
          <div className="mt-5 pb-3 border-bottom-black">
            {!ud_front && <p className="text-danger">Front side of Identity Card is not uploaded</p>}
            {ud_front && <a href={`http://localhost:8081/api/upload/${ud_front}`} target="_blank" className="d-block"><img src={`http://localhost:8081/api/upload/${ud_front}`} className="img-fluid" alt="Responsive image"/></a>}
          </div>
          <div className="mt-5 pb-3 border-bottom-black">
            {!ud_back && <p className="text-danger">Back side of Identity Card is not uploaded</p>}
            {ud_back && <a href={`http://localhost:8081/api/upload/${ud_back}`} target="_blank" className="d-block"><img src={`http://localhost:8081/api/upload/${ud_back}`} className="img-fluid" alt="Responsive image"/></a>}
          </div>

        </div>
        <div className="col-md-12 mt-3">
          <textarea onChange={this.onTextAreaChange} className="form-control" value={commentState}></textarea>
          <div className="mt-3">
            <button className={acceptBtnClass} onClick={this.onGeneralInfoEdit.bind(this, "VALID")}>SAVE AS VALID</button>
            <button className={rejectBtnClass} onClick={this.onGeneralInfoEdit.bind(this, "INVALID")}>SAVE AS INVALID</button>
          </div>
        </div>
      </div>
    )
  }
}

export default connect(
  state => ({
    lang: state.lang,
    currentStudentId: state.moderator.currentStudentId,
    personalInfo: state.moderator.personalInfo
  }),
  dispatch => ({
    fetchStudentPersonalInfoByModerator: bindActionCreators(fetchStudentPersonalInfoByModerator, dispatch),
    editGeneralInfo: bindActionCreators(editGeneralInfo, dispatch)
    // fetchStudentFullInfo: bindActionCreators(fetchStudentFullInfo, dispatch),
    // editGeneralInfo: bindActionCreators(editGeneralInfo, dispatch),
    // saveDocumentsComment: bindActionCreators(saveDocumentsComment, dispatch),
    // changeDocumentStatus: bindActionCreators(changeDocumentStatus, dispatch),
    // fetchTotalAmountOfStudents: bindActionCreators(fetchTotalAmountOfStudents, dispatch),
    // selectStudent: bindActionCreators(selectStudent, dispatch)
  })
)(EditStudentGeneralInfo)
