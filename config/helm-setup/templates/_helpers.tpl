{{- define "configmap.labels" }} 
  labels:
    date: {{ now | htmlDate}}
    version: 1.0.1
{{- end }}