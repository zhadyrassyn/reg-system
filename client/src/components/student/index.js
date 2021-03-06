import React, { Component } from "react"

import OverviewMenu from "./overview_menu"
import PersonalInfoForm from "./personal_info_form"
import EducationInfoForm from "./education_info_form"
import MedicalInfoForm from "./medical_info_form"
import {connect} from "react-redux"

import {
  FORM_PERSONAL_INFO,
  FORM_EDUCATION_INFO,
  FORM_MEDICAL_INFO
} from "../../constants"

class StudentApp extends Component {
  constructor(props) {
    super(props)

    this.state = {
      activeForm: FORM_PERSONAL_INFO
    }
  }

  changeForm = (form) => {
    this.setState({ activeForm: form })
  }
f
  render() {
    const activeForm = this.state.activeForm
    const {lang} = this.props

    return (
      <div>
        <OverviewMenu activeForm={activeForm} changeForm={this.changeForm.bind(this)} lang={lang}/>
        { activeForm === FORM_PERSONAL_INFO && <PersonalInfoForm /> }
        { activeForm === FORM_EDUCATION_INFO && <EducationInfoForm /> }
        { activeForm === FORM_MEDICAL_INFO && <MedicalInfoForm /> }
      </div>
    )
  }
}

export default connect(
  state => ({
    lang: state.lang
  }),
  dispatch => ({

  })
)(StudentApp)