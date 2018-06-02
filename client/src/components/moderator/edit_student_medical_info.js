import React, {Component} from "react"
import {bindActionCreators} from "redux"
import {connect} from "react-redux"
import {message} from "../../locale/message"
import {fetchStudentMedicalInfoByModerator, saveMedicalComment} from "../../actions";

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
    const {saveMedicalComment, currentStudentId} = this.props

    saveMedicalComment(currentStudentId, this.state.commentState, status,
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

          {!form63 && <p className="text-danger">{message.medical_form_63[lang]} {message.not_send[lang]}</p>}
          {form63 &&
            <div>
              <p>{message.medical_form_63[lang]}</p>
              <a href={`http://localhost:8081/api/upload/${form63}`} target="_blank" className="d-block"><img src={`http://localhost:8081/api/upload/${form63}`} className="img-fluid" alt="Responsive image"/></a>
            </div>
          }
          {!form86 && <p className="text-danger">{message.medical_form_86[lang]} {message.not_send[lang]}</p>}
          {form86 &&
            <div className="mt-5">
              <p>{message.medical_form_86[lang]}</p>
              <a href={`http://localhost:8081/api/upload/${form86}`} target="_blank" className="d-block"><img src={`http://localhost:8081/api/upload/${form86}`} className="img-fluid" alt="Responsive image"/></a>
            </div>
          }
          {!flurography && <p className="text-danger">{message.flurography[lang]} {message.not_send[lang]}</p>}
          {flurography &&
            <div className="mt-5">
              <p>{message.flurography[lang]}</p>
              <a href={`http://localhost:8081/api/upload/${flurography}`} target="_blank" className="d-block"><img src={`http://localhost:8081/api/upload/${flurography}`} className="img-fluid" alt="Responsive image"/></a>
            </div>
          }
        </div>
        <div className="col-md-12 mt-3">
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
    saveMedicalComment: bindActionCreators(saveMedicalComment, dispatch)
  })
)(EditStudentMedicalInfo)
