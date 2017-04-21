pipeline {
  agent {
    docker {
      image 'maven:3.3.9-jdk-8'
    }
    
  }
  stages {
    stage('init') {
      steps {
        echo 'hello world'
      }
    }
    stage('build') {
      steps {
        sh '''echo $JAVA_HOME
echo $MAVEN_HOME

mvn clean'''
      }
    }
  }
}