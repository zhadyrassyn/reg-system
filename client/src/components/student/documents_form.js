import React, { Component } from "react"
import { connect } from "react-redux"
import { bindActionCreators } from "redux"

import {
  DIPLOMA_CERTIFICATE,
  UNT_CT_CERTIFICATE,
  PHOTO_3x4,
  HEALTH_086,
  HEALTH_063,
  FLUOROGRAPHY,
  IDENTITY_CARD_FRONT,
  IDENTITY_CARD_BACK
} from "../../constants"

import {
  saveDocument
} from "../../actions"

class DocumentsForm extends Component {

  constructor(props) {
    super(props)

    this.state = ({
      documentType: ''
    })
  }

  exportFile = (e) => {
    e.preventDefault()

    this.setState({documentType: e.target.name}, () => {
      //script for opening 'choose file' dialog
      const elem = document.getElementById("exportFile")
      if(elem && document.createEvent) {
        const evt = document.createEvent("MouseEvents");
        evt.initEvent("click", true, false);
        elem.dispatchEvent(evt);
      }
    })

  }

  onFileChange = (e) => {
    const file = e.target.files[0]
    const documentType = this.state.documentType

    const { saveDocument } = this.props
    saveDocument(file, documentType)
  }

  render() {
    return (
      <div className="container">
        <ol>
          <li>
            <p><a href="#" onClick={this.exportFile} name={DIPLOMA_CERTIFICATE}>Certificate of secondary education completion or the original diploma of technical and vocational (primary or secondary vocational, post-secondary) education</a></p>
          </li>
          <li>
            <p><a href="#" onClick={this.exportFile} name={UNT_CT_CERTIFICATE}>Certificate of UNT / CT</a></p>
          </li>
          <li>
            <p><a href="#" onClick={this.exportFile} name={PHOTO_3x4}>Photo 3x4</a></p>
          </li>
          <li>
            <p><a href="#" onClick={this.exportFile} name={HEALTH_086}>Health certificate 086-U</a></p>
          </li>
          <li>
            <p><a href="#" onClick={this.exportFile} name={HEALTH_063}>Health certificate 0-63</a></p>
          </li>
          <li>
            <p><a href="#" onClick={this.exportFile} name={FLUOROGRAPHY}>fluorography</a></p>
          </li>
          <li>
            <p><a href="#" onClick={this.exportFile} name={IDENTITY_CARD_FRONT}>A copy of the identity card (front side)</a></p>
          </li>
          <li>
            <p><a href="#" onClick={this.exportFile} name={IDENTITY_CARD_BACK}>A copy of the identity card (back side)</a></p>
          </li>
        </ol>

        <input style={{display:'none'}} type="file" className="form-control-file" id="exportFile" onChange={this.onFileChange}/>
        <div className="form-group">
          <label htmlFor="comment">Review Comment</label>
          <textarea className="form-control" id="comment" rows="3" disabled={true}></textarea>
        </div>
      </div>
    )
  }
}

export default connect(
  state => ({

  }),
  dispatch => ({
    saveDocument: bindActionCreators(saveDocument, dispatch)
  })
)(DocumentsForm)