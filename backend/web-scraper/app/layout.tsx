'use client'

import { useEffect } from 'react'
import { initializeAnalytics } from '@/lib/firebase'

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  useEffect(() => {
    initializeAnalytics()
  }, [])

  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  )
}

