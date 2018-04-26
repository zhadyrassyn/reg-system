import React, { Component } from "react"

import OverviewMenu from "./overview_menu"
import GeneralInfoForm from "./general_info_form"
import DocumentsForm from "./documents_form"
import {connect} from "react-redux"

import {
  FORM_GENERAL,
  FORM_DOCUMENTS,

  FORM_PERSONAL_INFO,
  FORM_EDUCATION_INFO,
  FORM_CERTIFICATES_INFO,
  FORM_MEDICAL_INFO
} from "../../constants"

class StudentApp extends Component {
  constructor(props) {
    super(props)

    this.state = {
      activeForm: FORM_PERSONAL_INFO
    }
  }

  changeForm = (formType) => {
    this.setState({ activeForm: formType })
  }

  render() {
    const activeForm = this.state.activeForm
    const {lang} = this.props

    return (
      <div>
        <OverviewMenu activeForm={activeForm} changeForm={this.changeForm.bind(this)} lang={lang}/>
        { activeForm === FORM_PERSONAL_INFO && <GeneralInfoForm /> }
        { activeForm === FORM_EDUCATION_INFO && <GeneralInfoForm /> }
        { activeForm === FORM_CERTIFICATES_INFO && <GeneralInfoForm /> }
        { activeForm === FORM_MEDICAL_INFO && <DocumentsForm /> }

      </div>
    )
  }
}

StudentApp = connect(
  state => ({
    lang: state.lang
  }),
  dispatch => ({

  })
)(StudentApp)

export default StudentApp