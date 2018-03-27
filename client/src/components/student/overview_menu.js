import React, { Component } from "react"

import {
  FORM_GENERAL,
  FORM_DOCUMENTS
} from "../../constants"

export default class OverviewMenu extends Component{

  constructor(props) {
    super(props)
  }

  onGeneralInfoBtnClick = () => {
    this.props.changeForm(FORM_GENERAL)
  }

  onDocumentsBtnClick = () => {
    this.props.changeForm(FORM_DOCUMENTS)
  }

  render() {
    const activeForm = this.props.activeForm

    return (
      <div className="container">
        <div className="mt-4">
          <div className="nav justify-content-between">
            <div></div>
            <ul className="nav nav-pills">
              <li className="nav-item">
                <button
                  className={ "mr-2 nav-link btn " + (activeForm === FORM_GENERAL ? "active" : "") } onClick={this.onGeneralInfoBtnClick}>
                  General info
                </button>
              </li>
              <li className="nav-item">
                <button className={ "nav-link btn " + (activeForm === FORM_DOCUMENTS ? "active" : "") } onClick={this.onDocumentsBtnClick}>
                  Documents
                </button>
              </li>
            </ul>
          </div>
        </div>
      </div>
    )
  }
}
