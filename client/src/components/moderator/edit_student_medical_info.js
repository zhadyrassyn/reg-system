import React, {Component} from "react"
import {bindActionCreators} from "redux"
import {connect} from "react-redux"
import {message} from "../../locale/message"
import {fetchStudentMedicalInfoByModerator, saveEducationComment} from "../../actions";

class EditStudentMedicalInfo extends Component {

  constructor(props) {
    super(props)

    this.state = {
      commentState: this.props.medicalInfo.comment || ""
    }
  }

  componentDidMount() {
    const {currentStudentId, fetchStudentMedicalInfoByModerator} = this.props
    fetchStudentMedicalInfoByModerator(currentStudentId,
      () => {
        this.setState({commentState: this.props.medicalInfo.comment})
        console.log('success on fetchStudentMedicalInfoByModerator')
      },
      () => {
        console.log('fail on fetchStudentMedicalInfoByModerator')
      }
    )
  }

  saveComment = (status) => {
    const {saveEducationComment, currentStudentId} = this.props

    saveEducationComment(currentStudentId, this.state.commentState, status,
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
    const {currentStudentId, lang, medicalInfo} = this.props
    console.log('medical info', medicalInfo)

    const {comment, status, form63, form86, flurography} = medicalInfo

    const commentState = this.state.commentState

    const acceptBtnClass = "btn " + (status === "VALID" && comment === commentState ? "btn-success" : "btn-outline-success")
    const rejectBtnClass = "btn ml-2 " + (status === "INVALID" && comment === commentState ? "btn-danger" : "btn-outline-danger")

    return (
      <div className="row mt-5">
        <div className="col-md-12">
          {/*{!schoolDiploma && <p className="text-danger">{message.diploma_certificate[lang]} {message.not_send[lang]}</p>}*/}
          {/*{schoolDiploma && <img src={`http://localhost:8081/api/upload/${schoolDiploma}`} className="img-fluid" alt="Responsive image"/>}*/}
          {/*{!entCertificate && <p className="text-danger">{message.ent_certificate[lang]} {message.not_send[lang]}</p>}*/}
          {/*{entCertificate && <img src={`http://localhost:8081/api/upload/${entCertificate}`} className="img-fluid" alt="Responsive image"/>}*/}
        </div>
        <div className="col-md-12">
          <textarea onChange={this.onTextAreaChange} className="form-control" value={commentState}></textarea>
          <div className="mt-3">
            <button className={acceptBtnClass} onClick={this.saveComment.bind(this, "VALID")}>SAVE AS VALID</button>
            <button className={rejectBtnClass} onClick={this.saveComment.bind(this, "INVALID")}>SAVE AS INVALID</button>
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
    medicalInfo: state.moderator.medicalInfo
  }),
  dispatch => ({
    fetchStudentMedicalInfoByModerator: bindActionCreators(fetchStudentMedicalInfoByModerator, dispatch),
    // saveEducationComment: bindActionCreators(saveEducationComment, dispatch)
  })
)(EditStudentMedicalInfo)
