import React, { Component } from "react"
import EditStudentDocuments from "./edit_student_documents"
import EditStudentGeneralInfo from "./edit_student_general_Info"
import {message} from "../../locale/message"

import {
  FORM_PERSONAL_INFO,
  FORM_EDUCATION_INFO,
  FORM_MEDICAL_INFO
} from "../../constants"



class EditStudent extends Component {
  constructor(props) {
    super(props)

    this.state = {
      activeForm: FORM_PERSONAL_INFO,
    }
  }

  changeForm = (e) => {
    this.setState({activeForm: e.target.name})
  }

  render() {
    const { closeModal, selectedStudent, onGeneralInfoEdit, onSaveDocumentsComment, onDocumentStatusChange, lang } = this.props
    console.log('selectedStudent ', selectedStudent)

    const documents = _.mapKeys(selectedStudent.documents, "type")
    const activeForm = this.state.activeForm
    return (
      <div className="container">
        <button onClick={closeModal} className="close" type="button"/>
        <div className="mt-4">
          <div className="nav justify-content-between">
            <ul className="nav nav-pills">
              <li className="nav-item">
                <button name={FORM_PERSONAL_INFO}
                  className={ "mr-2 nav-link btn " + (activeForm === FORM_PERSONAL_INFO ? "active" : "btn-info") } onClick={this.changeForm}>
                  {message.personal_info[lang]}
                </button>
              </li>
              <li className="nav-item">
                <button name={FORM_EDUCATION_INFO}
                  className={ "mr-2 nav-link btn " + (activeForm === FORM_EDUCATION_INFO ? "active" : "btn-info") } onClick={this.changeForm}>
                  {message.education_info[lang]}
                </button>
              </li>
              <li className="nav-item">
                <button name={FORM_MEDICAL_INFO}
                        className={ "nav-link btn " + (activeForm === FORM_MEDICAL_INFO ? "active" : "btn-info") } onClick={this.changeForm}>
                  {message.medical_info[lang]}
                </button>
              </li>
            </ul>
          </div>
        </div>

        <div className="row mt-3">
          <div className="col-md-12">
            {activeForm === FORM_PERSONAL_INFO &&
              <EditStudentGeneralInfo/>
            }
            {/*{activeForm === FORM_DOCUMENTS &&*/}
              {/*<EditStudentDocuments*/}
                {/*selectedStudent={ selectedStudent }*/}
                {/*onSaveDocumentsComment={ onSaveDocumentsComment }*/}
                {/*onDocumentStatusChange = { onDocumentStatusChange }*/}
              {/*/>*/}
            {/*}*/}
          </div>
        </div>
      </div>
    )
  }
}

export default EditStudent