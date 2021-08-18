pipeline {

  agent any

  stages {
    stage("Clean") {
      steps {
        echo 'Cleaning the application...'
        withMaven() {
          sh './mvnw clean'
        }
      }
    }

    stage("Test") {
      steps {
        echo 'Testing the application...'
        withMaven() {
          sh './mvnw test'
        }
      }
    }

    stage("Package") {
      steps {
        echo 'Packaging the application...'
        withMaven() {
          sh './mvnw package'
        }
      }
    }
  }

}
