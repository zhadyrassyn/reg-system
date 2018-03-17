import React, { Component } from "react"
import "./overview_menu"
import OverviewMenu from "./overview_menu"
import Select from "react-select"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"
import {Field, reduxForm} from "redux-form"

import {
  fetchCities,
  fetchSchools
} from "../../actions"

class StudentApp extends Component {
  constructor(props) {
    super(props)
  }

  componentDidMount() {
    const { fetchCities } = this.props
    fetchCities()
  }

  renderField(field) {
    console.log(field.input)
    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <input type={field.type} className="form-control" name={field.name} placeholder={field.placeholder} id={field.id} {...field.input}/>
      </div>
    )
  }

  renderSelect(field) {
    // const {form} = this.props
    // console.log(form)
    return (
      <div className="col">
        <label htmlFor={field.id}>{field.label}</label>
        <Select
          value="Qyzylorda"
          // disabled={schoolSelectDisabled}
          name={field.name}
          onChange={field.onChange}
          options={field.options}
          {...field.input}
        />
      </div>
    )
  }

  handleCityChange = (city) => {
      const {fetchSchools} = this.props
      fetchSchools(city.value,
        () => {
        },
        () => {
          console.log('error on fetching schools')
        })

    }

  handleSchoolChange = (school) => {
    this.setState({ school })
  }

  onSubmit(values) {
    console.log('Values ', values)
  }

  render() {

    const { cities, schools } = this.props
    const { handleSubmit, submitting} = this.props;

    return (
      <div className="container">
        <OverviewMenu/>
        <form onSubmit={handleSubmit(this.onSubmit.bind(this))}>
          <div className="form-row">
            <Field
              label="First name"
              name="firstName"
              id="firstName"
              type="text"
              placeholder="First name"
              component={this.renderField}
            />
            <Field
              label="Middle name"
              name="middleName"
              id="middleName"
              type="text"
              placeholder="Middle name"
              component={this.renderField}
            />
            <Field
              label="Last name"
              name="lastName"
              id="lastName"
              type="text"
              placeholder="Last name"
              component={this.renderField}
            />
          </div>
          <div className="form-row mt-3">
            <Field
              label="Date of birth"
              name="birthDate"
              id="birthDate"
              type="date"
              component={this.renderField}
            />
          </div>
          <div className="form-row mt-3">
            <Field
              label="City"
              name="city"
              id="city"
              options={cities}
              component={this.renderSelect}
              onChange={this.handleCityChange}
              onBlur={e => { e.preventDefault() }}
            />

            {/*<div className="col">*/}
              {/*<label htmlFor="city">City</label>*/}
              {/*<Select*/}
                {/*name="city"*/}
                {/*value={cityValue}*/}
                {/*onChange={this.handleCityChange}*/}
                {/*options={cities}*/}
              {/*/>*/}
            {/*</div>*/}
          </div>
          <div className="form-row mt-3">
            <Field
              label="School"
              name="school"
              id="school"
              options={schools}
              component={this.renderSelect}
              onBlur={e => { e.preventDefault() }}
            />
              {/*<label htmlFor="school">School</label>*/}
              {/*<Select*/}
                {/*value={schoolValue}*/}
                {/*disabled={schoolSelectDisabled}*/}
                {/*name="school"*/}
                {/*onChange={this.handleSchoolChange}*/}
                {/*options={schools}*/}
              {/*/>*/}
            <div className="col">
              <label htmlFor="anotherSchool">Not finding your school? Write down</label>
              <input type="text" className="form-control"/>
            </div>
          </div>
          <div className="col text-right">
            <button className="btn btn-success mt-3" type="submit" disabled={submitting}>Save</button>
          </div>
        </form>
      </div>
    )
  }
}


export default reduxForm({
  form: 'GeneralInfo save'
})(connect(
  state => ({
    cities: state.info.cities,
    schools: state.info.schools,
    form: state.form
  }),
  dispatch => ({
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch)
  })
)(StudentApp))