pipeline {

  agent any

  stages {
    stage("Clean") {
      steps {
        echo 'Cleaning the application...'
      }
    }

    stage("Package") {
      steps {
        echo 'Packaging the application...'
        withMaven() {
          sh 'mvn clean install'
        }
      }
    }
  }

}
