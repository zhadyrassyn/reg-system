import React, {Component} from "react"
import {bindActionCreators} from "redux"
import {connect} from "react-redux"
import {message} from "../../locale/message"
import {fetchStudentEducationInfoByModerator, saveEducationComment} from "../../actions";

class EditStudentEducationInfo extends Component {

  constructor(props) {
    super(props)

    this.state = {
      commentState: this.props.educationInfo.comment || ""
    }
  }

  componentDidMount() {
    const {currentStudentId, fetchStudentEducationInfoByModerator} = this.props
    fetchStudentEducationInfoByModerator(currentStudentId,
      () => {
        this.setState({commentState: this.props.educationInfo.comment})
        console.log('success on fetchStudentEducationInfoByModerator')
      },
      () => {
        console.log('fail on fetchStudentEducationInfoByModerator')
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
    const {currentStudentId, lang, educationInfo} = this.props

    console.log('education info', educationInfo)
    const {educationArea, city, school, ent_amount, ent_certificate_number, ikt, faculty, speciality, school_finish, schoolDiploma,
      entCertificate, comment, status} = educationInfo

    const commentState = this.state.commentState

    const acceptBtnClass = "btn " + (status === "VALID" && comment === commentState ? "btn-success" : "btn-outline-success")
    const rejectBtnClass = "btn ml-2 " + (status === "INVALID" && comment === commentState ? "btn-danger" : "btn-outline-danger")

    let area_text = ""
    let city_text = ""
    let school_text = ""
    let faculty_text = ""
    let specialty_text = ""
    if(educationArea) {
      area_text = lang === 'ru' ? educationArea.nameRu : lang === 'en' ? educationArea.nameEn : educationArea.nameKk
    }
    if(city) {
      city_text = lang === 'ru' ? city.nameRu : lang === 'en' ? city.nameEn : city.nameKk
    }
    if(school) {
      school_text = lang === 'ru' ? school.nameRu : lang === 'en' ? school.nameEn : school.nameKk
    }
    if(faculty) {
      faculty_text = lang === 'ru' ? faculty.nameRu : lang === 'en' ? faculty.nameEn : faculty.nameKk
    }
    if(speciality) {
      specialty_text = lang === 'ru' ? speciality.nameRu : lang === 'en' ? speciality.nameEn : speciality.nameKk
    }

    return (
      <div className="row mt-5">
        <div className="col-md-6">
          <p>{message.area[lang]} : {area_text}</p>
          <p>{message.city[lang]} : {city_text}</p>
          <p>{message.school[lang]} : {school_text}</p>
          <p>{message.school_finish[lang]} : {school_finish}</p>
          <p>{message.ent_certificate[lang]} : {ent_certificate_number}</p>
          <p>{message.ent_amount[lang]} : {ent_amount}</p>
          <p>{message.ikt[lang]} : {ikt}</p>
          <p>{message.choose_faculty[lang]} : {faculty_text}</p>
          <p>{message.choose_speciality[lang]} : {specialty_text}</p>
        </div>
        <div className="col-md-6">
          {!schoolDiploma && <p className="text-danger">{message.diploma_certificate[lang]} {message.not_send[lang]}</p>}
          {schoolDiploma && <img src={`http://localhost:8081/api/upload/${schoolDiploma}`} className="img-fluid" alt="Responsive image"/>}
          {!entCertificate && <p className="text-danger">{message.ent_certificate[lang]} {message.not_send[lang]}</p>}
          {entCertificate && <img src={`http://localhost:8081/api/upload/${entCertificate}`} className="img-fluid" alt="Responsive image"/>}
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
    educationInfo: state.moderator.educationInfo
  }),
  dispatch => ({
    fetchStudentEducationInfoByModerator: bindActionCreators(fetchStudentEducationInfoByModerator, dispatch),
    saveEducationComment: bindActionCreators(saveEducationComment, dispatch)
  })
)(EditStudentEducationInfo)
