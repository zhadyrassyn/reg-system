import React, {Component} from "react"
import {bindActionCreators} from "redux"
import {connect} from "react-redux"
import {message} from "../../locale/message"
import {
  ACCEPTED,
  REJECTED
} from "../../constants"

import {fetchStudentPersonalInfoByModerator, editGeneralInfo} from "../../actions";

class EditStudentEducationInfo extends Component {

  constructor(props) {
    super(props)

    this.state = {
      // commentState: this.props.personalInfo.comment || ""
    }
  }

  componentDidMount() {
    // const {currentStudentId, fetchStudentPersonalInfoByModerator} = this.props
    // fetchStudentPersonalInfoByModerator(currentStudentId,
    //   () => {
    //     this.setState({commentState: this.props.personalInfo.comment})
    //     console.log('success on fetchStudentPersonalInfoByModerator')
    //   },
    //   () => {
    //     console.log('fail on fetchStudentPersonalInfoByModerator')
    //   }
    // )
  }

  onGeneralInfoEdit = (status) => {
    // const {editGeneralInfo, currentStudentId} = this.props
    //
    // editGeneralInfo(currentStudentId, this.state.commentState, status,
    //   () => {
    //     console.log('success 123')
    //   },
    //   () => {
    //     console.log('failure 123')
    //   })
  }

  onTextAreaChange = (event) => {
    // this.setState({commentState: event.target.value})
  }

  render() {
    // const {currentStudentId, lang, personalInfo} = this.props
    //
    // const {firstName, middleName, lastName, gender, birthDate, givenDate, givenPlace, iin, ud_number, mobilePhone,
    //   telPhone, nationality, birthPlace, blood_group, citizenship, factStreet, factHouse, factFraction, factFlat, regStreet, regHouse, regFraction,
    //   regFlact, ud_front, ud_back, photo3x4, comment, status} = personalInfo

    // console.log('current student id', currentStudentId)
    // console.log('personal info ', personalInfo)
    // const comment = this.state.comment
    // const com = personalInfo.comment
    // console.log('com ', com)
    // const commentState = this.state.commentState
    //
    //
    // const acceptBtnClass = "btn " + (status === "VALID" && comment === commentState ? "btn-success" : "btn-outline-success")
    // const rejectBtnClass = "btn ml-2 " + (status === "INVALID" && comment === commentState ? "btn-danger" : "btn-outline-danger")


    return (
      <div className="row mt-5">
        <div className="col-md-6">
          Hello, EducationInfo model
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
)(EditStudentEducationInfo)
