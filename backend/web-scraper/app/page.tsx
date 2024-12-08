'use client'

import { useState } from 'react'

export default function Home() {
  const [isLoading, setIsLoading] = useState(false)
  const [message, setMessage] = useState('')
  const [stats, setStats] = useState<{ newsCount: number; downloadsCount: number } | null>(null)

  const handleScrape = async () => {
    setIsLoading(true)
    setMessage('')
    setStats(null)

    try {
      const response = await fetch('/api/scrape', { method: 'POST' })
      const data = await response.json()
      setMessage(data.message || 'Scraping completed')
      setStats({ newsCount: data.newsCount, downloadsCount: data.downloadsCount })
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (error) {
      setMessage('An error occurred during scraping')
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <main className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Egerton University Web Scraper</h1>
      <button
        onClick={handleScrape}
        disabled={isLoading}
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
      >
        {isLoading ? 'Scraping...' : 'Start Scraping'}
      </button>
      {message && <p className="mt-4">{message}</p>}
      {stats && (
        <div className="mt-4">
          <p>News items scraped: {stats.newsCount}</p>
          <p>Download items scraped: {stats.downloadsCount}</p>
        </div>
      )}
    </main>
  )
}

